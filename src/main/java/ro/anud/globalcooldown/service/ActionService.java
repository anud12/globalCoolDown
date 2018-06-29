package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.model.action.ActionOnPawn;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActionService {
    private static Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
    private ActionOnPawnRepository actionOnPawnRepository;

    public void queue(ActionOnPawn model) {
        ActionOnPawnEntity entity = model.toEntity();
        entity.setSaveDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        actionOnPawnRepository.findFirstByPawnIdAndNameOrderBySaveDateTimeDesc(entity.getPawnId(), entity.getName())
                .ifPresent(actionOnPawnEntity -> {
                    entity.setParent(actionOnPawnEntity);
                    LOGGER.debug("QUEUED " + actionOnPawnEntity.getId());
                });
        ActionOnPawnEntity savedEntity = actionOnPawnRepository.save(entity);
        LOGGER.debug("SAVED " + savedEntity.getId());
    }

    public void override(ActionOnPawn actionOnPawn) {
        ActionOnPawnEntity entity = actionOnPawn.toEntity();
        actionOnPawnRepository.findFirstByPawnIdAndNameOrderBySaveDateTimeDesc(entity.getPawnId(), entity.getName())
                .ifPresent(actionOnPawnEntity -> {
                    LOGGER.debug("DELETED " + actionOnPawnEntity.getId());
                    deleteWithParents(actionOnPawnEntity);
                });

        entity.setSaveDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        ActionOnPawnEntity savedEntity = actionOnPawnRepository.save(entity);
        LOGGER.debug("SAVED " + savedEntity.getId());
    }

    public void updateAll() {
        /*List<ActionOnPawnEntity> actionOnPawnEntityList = actionOnPawnRepository.findAll()
                .stream()
                .filter(actionOnPawnEntity -> actionOnPawnEntity.getEffectOnPawnEntityList()
                        .isEmpty())
                .peek(actionOnPawnEntity -> LOGGER.debug(
                        "DELETING " + actionOnPawnEntity.getId()))
                .collect(Collectors.toList());*/

        List<ActionOnPawnEntity> actionOnPawnEntityList = actionOnPawnRepository.findAll()
                .stream()
                .filter(actionOnPawnEntity -> actionOnPawnEntity.getEffectOnPawnEntityList()
                        .stream()
                        .filter(effectOnPawnEntity -> !effectOnPawnEntity.getIsSideEffect())
                        .collect(Collectors.toList())
                        .isEmpty())
                .peek(actionOnPawnEntity -> LOGGER.debug(
                        "DELETING " + actionOnPawnEntity.getId()))
                .collect(Collectors.toList());

        List<ActionOnPawnEntity> parentActions = actionOnPawnRepository.findAllByParentIn(actionOnPawnEntityList)
                .stream()
                .peek(actionOnPawnEntity -> actionOnPawnEntity.setParent(null))
                .collect(Collectors.toList());
        actionOnPawnRepository.save(parentActions);

        actionOnPawnRepository.delete(actionOnPawnEntityList);
    }

    public void deleteById(Long id) {
        actionOnPawnRepository.delete(id);
    }

    private void deleteWithParents(ActionOnPawnEntity actionOnPawnEntity) {
        List<ActionOnPawnEntity> actionOnPawnEntityList = new ArrayList<>();
        actionOnPawnEntityList.add(actionOnPawnEntity);
        while (actionOnPawnEntity.getParent() != null) {
            ActionOnPawnEntity parent = actionOnPawnEntity.getParent();
            actionOnPawnEntity.setParent(null);
            actionOnPawnEntityList.add(parent);
            actionOnPawnEntity = parent;
        }
        actionOnPawnRepository.delete(actionOnPawnEntityList);
    }

}

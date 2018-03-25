package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.model.action.ActionOnPawnInputModel;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActionService {
    private static Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
    private ActionOnPawnRepository actionOnPawnRepository;

    public void queue(ActionOnPawnInputModel model) {
        ActionOnPawnEntity entity = model.toEntity();
        entity.setSaveDateTime(LocalDateTime.now().toString());
        actionOnPawnRepository.findFirstByNameOrderBySaveDateTimeDesc(entity.getName())
                .ifPresent(actionOnPawnEntity -> {
                    entity.setParent(actionOnPawnEntity);
                    LOGGER.debug("QUEUED " + actionOnPawnEntity.getId());
                });
        ActionOnPawnEntity savedEntity = actionOnPawnRepository.save(entity);
        LOGGER.debug("SAVED " + savedEntity.getId());
    }

    public void updateAll() {
        List<ActionOnPawnEntity> actionOnPawnEntityList = actionOnPawnRepository.findAll()
                .stream()
                .filter(actionOnPawnEntity -> actionOnPawnEntity.getEffectOnPawnEntityList()
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
}

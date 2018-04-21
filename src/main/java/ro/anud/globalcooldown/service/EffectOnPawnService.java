package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.repository.ConditionOnPawnRepository;
import ro.anud.globalcooldown.repository.EffectOnPawnRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EffectOnPawnService {
    private static Logger LOGGER = LoggerFactory.getLogger(EffectOnPawnService.class);
    private EffectOnPawnRepository effectOnPawnRepository;
    private ConditionOnPawnRepository conditionOnPawnRepository;

    @PostConstruct
    public void onInit() {
    }

    public List<EffectOnPawn> getAll() {
        return effectOnPawnRepository.findAll()
                .stream()
                .peek(effectOnPawn -> LOGGER.debug("GETTING " + effectOnPawn.getId()))
                .map(EffectOnPawnEntity::toAction)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<EffectOnPawn> updateAll(List<EffectOnPawn> effectOnPawnList) {
        List<EffectOnPawnEntity> toBeDeletedList = effectOnPawnList.stream()
                .filter(EffectOnPawn::isRemovable)
                .peek(effectOnPawn -> LOGGER.debug(
                        "DELETING " + effectOnPawn.getId()))
                .map(EffectOnPawn::toEntity)
                .collect(Collectors.toList());
        effectOnPawnRepository.deleteInBatch(toBeDeletedList);
        return effectOnPawnRepository.save(effectOnPawnList.stream()
                                                   .filter(action -> !action.isRemovable())
                                                   .map(EffectOnPawn::toEntity)
                                                   .peek(effectOnPawnEntity -> {
                                                       ActionOnPawnEntity actionOnPawnEntity = effectOnPawnRepository.findOne(
                                                               effectOnPawnEntity.getId()).getAction();
                                                       effectOnPawnEntity.setAction(actionOnPawnEntity);
                                                   })
                                                   .peek(effectOnPawn -> LOGGER.debug("SAVING " + effectOnPawn.getId()))
                                                   .collect(Collectors.toList())
        ).stream()
                .map(EffectOnPawnEntity::toAction)
                .collect(Collectors.toList());

    }
}

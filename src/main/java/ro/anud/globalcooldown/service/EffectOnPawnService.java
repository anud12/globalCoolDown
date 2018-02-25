package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.repository.EffectOnPawnRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EffectOnPawnService {
	private static Logger LOGGER = LoggerFactory.getLogger(EffectOnPawnService.class);
	private EffectOnPawnRepository effectOnPawnRepository;

	@PostConstruct
	public void onInit() {
	}

	public List<EffectOnPawn> getAll() {
		return effectOnPawnRepository.findAll()
				.stream()
				.map(EffectOnPawnEntity::toAction)
				.collect(Collectors.toList());
	}

	public List<EffectOnPawn> updateAll(List<EffectOnPawn> effectOnPawnList) {
		effectOnPawnRepository.delete(effectOnPawnList.stream()
				.filter(EffectOnPawn::isArrived)
				.map(EffectOnPawn::toEntity)
				.collect(Collectors.toList()));
		return effectOnPawnRepository.save(effectOnPawnList.stream()
				.filter(action -> !action.isArrived())
				.map(EffectOnPawn::toEntity)
				.peek(effectOnPawnEntity -> {
					ActionOnPawnEntity actionOnPawnEntity = effectOnPawnRepository.findOne(effectOnPawnEntity.getId()).getAction();
					effectOnPawnEntity.setAction(actionOnPawnEntity);
				})
				.collect(Collectors.toList())
		).stream()
				.map(EffectOnPawnEntity::toAction)
				.collect(Collectors.toList());

	}

	public void save(EffectOnPawn effectOnPawn) {
		LOGGER.info("saving " + effectOnPawn);
		effectOnPawnRepository.save(effectOnPawn.toEntity());
	}
}

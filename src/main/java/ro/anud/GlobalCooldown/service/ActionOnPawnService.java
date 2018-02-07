package ro.anud.GlobalCooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.GlobalCooldown.action.ActionOnPawn;
import ro.anud.GlobalCooldown.entity.ActionOnPawnEntity;
import ro.anud.GlobalCooldown.model.ActionOnPawnModel;
import ro.anud.GlobalCooldown.repository.ActionOnPawnRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActionOnPawnService {
	private static Logger LOGGER = LoggerFactory.getLogger(ActionOnPawnService.class);
	private ActionOnPawnRepository actionOnPawnRepository;

	@PostConstruct
	public void onInit() {
	}

	public List<ActionOnPawn> getAll() {
		return actionOnPawnRepository.findAll()
				.stream()
				.map(ActionOnPawnEntity::toAction)
				.collect(Collectors.toList());
	}

	public List<ActionOnPawn> updateAll(List<ActionOnPawn> actionOnPawnList) {
		actionOnPawnRepository.delete(actionOnPawnList.stream()
				.filter(ActionOnPawn::isArrived)
				.map(ActionOnPawn::toEntity)
				.collect(Collectors.toList()));
		return actionOnPawnRepository.save(actionOnPawnList.stream()
				.filter(action -> !action.isArrived())
				.map(ActionOnPawn::toEntity)
				.collect(Collectors.toList())
		).stream()
				.map(ActionOnPawnEntity::toAction)
				.collect(Collectors.toList());

	}

	public void save(ActionOnPawn actionOnPawn) {
		LOGGER.info("saving " + actionOnPawn);
		actionOnPawnRepository.save(actionOnPawn.toEntity());
	}
	public void save (ActionOnPawnModel actionOnPawnModel) {
		actionOnPawnRepository.save(actionOnPawnModel.toEntity());
	}
}

package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.mapper.ActionOnPawnMapper;
import ro.anud.globalcooldown.model.ActionModel;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActionService {
	private static Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
	private ActionOnPawnRepository actionOnPawnRepository;

	public ActionModel save(ActionOnPawn model) {
		ActionOnPawnEntity entity = model.toEntity();
		ActionOnPawnEntity savedEntity = actionOnPawnRepository.save(entity);
		return ActionOnPawnMapper.toActionModel(savedEntity);
	}

	public void updateAll() {

		actionOnPawnRepository.delete(actionOnPawnRepository.findAll()
				.stream()
				.filter(actionOnPawnEntity -> actionOnPawnEntity.getEffectOnPawnEntityList().isEmpty())
				.collect(Collectors.toList())
		);
	}

	public void deleteById(Long id) {
		actionOnPawnRepository.delete(id);
	}
}

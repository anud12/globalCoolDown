package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.model.action.ActionOnPawnInputModel;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.mapper.ActionOnPawnMapper;
import ro.anud.globalcooldown.model.action.ActionOnPawnOutputModel;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActionService {
	private static Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
	private ActionOnPawnRepository actionOnPawnRepository;

	public ActionOnPawnOutputModel save(ActionOnPawnInputModel model) {
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

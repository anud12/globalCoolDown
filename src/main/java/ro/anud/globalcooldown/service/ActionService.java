package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;

@Service
@AllArgsConstructor
public class ActionService {
	private ActionOnPawnRepository actionOnPawnRepository;

	public void save(ActionOnPawn model) {
		ActionOnPawnEntity entity = model.toEntity();
		actionOnPawnRepository.save(entity);
	}
}

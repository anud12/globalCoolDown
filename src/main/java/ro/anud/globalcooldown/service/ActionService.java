package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.model.ActionOnPawnModel;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;
import ro.anud.globalcooldown.repository.EffectOnPawnRepository;

@Service
@AllArgsConstructor
public class ActionService {
	private ActionOnPawnRepository actionOnPawnRepository;
	private EffectOnPawnRepository effectOnPawnRepository;

	public void save(ActionOnPawnModel model) {
		ActionOnPawnEntity entity = model.toEntity();
//		effectOnPawnRepository.save(entity.getEffectOnPawnEntityList());
		actionOnPawnRepository.save(entity);
	}
}

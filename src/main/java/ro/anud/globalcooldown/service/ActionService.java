package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;

@Service
@AllArgsConstructor
public class ActionService {
	private ActionOnPawnRepository actionOnPawnRepository;
}

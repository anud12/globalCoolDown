package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.anud.globalcooldown.model.ActionOnPawnModel;
import ro.anud.globalcooldown.service.ActionService;

@RestController
@AllArgsConstructor
@RequestMapping("/action")
public class ActionController {

	private ActionService actionService;

	@PostMapping
	public void add(@RequestBody final ActionOnPawnModel actionOnPawnModel) {
		actionService.save(actionOnPawnModel);
	}
}

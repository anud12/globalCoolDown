package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.model.ActionModel;
import ro.anud.globalcooldown.service.ActionService;

@RestController
@AllArgsConstructor
@RequestMapping("/action")
public class ActionController {

	private ActionService actionService;

	@PostMapping
	public ActionModel add(@RequestBody final ActionOnPawn actionOnPawn) {
		return actionService.save(actionOnPawn);
	}

	@DeleteMapping()
	public void delete(@RequestBody final Long id) {
		actionService.deleteById(id);
	}
}

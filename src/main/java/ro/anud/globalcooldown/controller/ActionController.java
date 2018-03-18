package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalcooldown.model.action.ActionOnPawnInputModel;
import ro.anud.globalcooldown.model.action.ActionOnPawnOutputModel;
import ro.anud.globalcooldown.service.ActionService;

@RestController
@AllArgsConstructor
@RequestMapping("/action")
@CrossOrigin
public class ActionController {

	private ActionService actionService;

	@PostMapping
	public ActionOnPawnOutputModel add(@RequestBody final ActionOnPawnInputModel actionOnPawnInputModel) {
		return actionService.save(actionOnPawnInputModel);
	}

	@DeleteMapping()
	public void delete(@RequestBody final Long id) {
		actionService.deleteById(id);
	}
}

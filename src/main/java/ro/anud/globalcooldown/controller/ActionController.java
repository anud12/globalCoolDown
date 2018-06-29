package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalcooldown.filter.ActionOnPawnFilter;
import ro.anud.globalcooldown.model.action.ActionOnPawn;
import ro.anud.globalcooldown.service.ActionService;

@RestController
@AllArgsConstructor
@RequestMapping("/action")
@CrossOrigin
public class ActionController {

	private ActionService actionService;
	private ActionOnPawnFilter actionOnPawnFilter;

	@PostMapping("/queue")
	public void queue(@RequestBody final ActionOnPawn actionOnPawn) {
		actionOnPawnFilter.filter(actionOnPawn)
				.ifPresent(aBoolean -> actionService.queue(actionOnPawn));
	}

	@PostMapping("/override")
	public void override(@RequestBody final ActionOnPawn actionOnPawn) {
		actionOnPawnFilter.filter(actionOnPawn)
				.ifPresent(aBoolean -> actionService.override(actionOnPawn));
	}

	@DeleteMapping()
	public void delete(@RequestBody final Long id) {
		actionService.deleteById(id);
	}
}

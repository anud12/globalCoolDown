package ro.anud.GlobalCooldown.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.anud.GlobalCooldown.model.ActionOnPawnModel;
import ro.anud.GlobalCooldown.service.ActionOnPawnService;

@RestController
@AllArgsConstructor
public class ActionController {

	private static Logger LOGGER = LoggerFactory.getLogger(ActionController.class);

	private ActionOnPawnService actionOnPawnService;
	@PostMapping("/addAction")
	public ResponseEntity addAction (@RequestBody final ActionOnPawnModel actionOnPawn) {
		LOGGER.info("adding actionOnPawn " + actionOnPawn);
		actionOnPawnService.save(actionOnPawn);
		return ResponseEntity.ok(null);
	}
}

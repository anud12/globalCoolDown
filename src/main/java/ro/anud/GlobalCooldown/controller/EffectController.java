package ro.anud.GlobalCooldown.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.anud.GlobalCooldown.model.EffectOnPawnModel;
import ro.anud.GlobalCooldown.service.EffectOnPawnService;

@RestController
@AllArgsConstructor
public class EffectController {

	private static Logger LOGGER = LoggerFactory.getLogger(EffectController.class);

	private EffectOnPawnService effectOnPawnService;
	@PostMapping("/addAction")
	public ResponseEntity addAction (@RequestBody final EffectOnPawnModel actionOnPawn) {
		LOGGER.info("adding actionOnPawn " + actionOnPawn);
		effectOnPawnService.save(actionOnPawn);
		return ResponseEntity.ok(null);
	}
}

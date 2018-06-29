package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalcooldown.model.action.ActionOnPawn;
import ro.anud.globalcooldown.service.EffectOnPawnService;

@RestController
@AllArgsConstructor
@RequestMapping("/effect")
public class EffectController {

    private static Logger LOGGER = LoggerFactory.getLogger(EffectController.class);

    private EffectOnPawnService effectOnPawnService;

    @PostMapping
    public ResponseEntity addAction(@RequestBody final ActionOnPawn actionOnPawn) {
        LOGGER.info("adding actionOnPawn " + actionOnPawn);
        return ResponseEntity.ok(null);
    }
}

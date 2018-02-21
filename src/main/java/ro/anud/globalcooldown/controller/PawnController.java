package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.service.PawnService;

import java.util.List;

@RestController
@AllArgsConstructor

public class PawnController {

	private static Logger LOGGER = LoggerFactory.getLogger(PawnController.class);

	private PawnService pawnService;
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/pawn")
	@SendTo("/world")
	public List<Pawn> getAll(final String message) {
		System.out.println(message);
		simpMessagingTemplate.convertAndSend("/app/world", pawnService.getAll());
		return pawnService.getAll();
	}


}

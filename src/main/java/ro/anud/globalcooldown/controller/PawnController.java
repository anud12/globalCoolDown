package ro.anud.globalcooldown.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.anud.globalcooldown.mapper.PawnMapper;
import ro.anud.globalcooldown.model.pawn.PawnOutputModel;
import ro.anud.globalcooldown.service.PawnService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor

public class PawnController {

    private static Logger LOGGER = LoggerFactory.getLogger(PawnController.class);

    private PawnService pawnService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/pawn")
    @SendTo("/world")
    public List<PawnOutputModel> getAll(final String message) {
        System.out.println(message);
        simpMessagingTemplate.convertAndSend("/app/world", pawnService.getAll().stream()
                                                                      .map(PawnMapper::toPawnOutputModel)
                                                                      .collect(Collectors.toList()));
        return pawnService.getAll().stream().map(PawnMapper::toPawnOutputModel)
				.collect(Collectors.toList());
    }

    @SubscribeMapping("/world")
	public List<PawnOutputModel> pawnSubscription() {
    	System.out.println("onSubscribe");
    	return pawnService.getAll().stream().map(PawnMapper::toPawnOutputModel)
				.collect(Collectors.toList());
	}

}

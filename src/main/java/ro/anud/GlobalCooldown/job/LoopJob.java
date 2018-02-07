package ro.anud.GlobalCooldown.job;

import com.vividsolutions.jts.geom.Coordinate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.GlobalCooldown.action.ActionOnPawn;
import ro.anud.GlobalCooldown.entity.Pawn;
import ro.anud.GlobalCooldown.service.ActionOnPawnService;
import ro.anud.GlobalCooldown.service.PawnService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoopJob {

	private static Logger LOGGER = LoggerFactory.getLogger(LoopJob.class);
	private PawnService pawnService;
	private ActionOnPawnService actionOnPawnService;
	private SimpMessagingTemplate simpMessagingTemplate;


	@Scheduled(fixedRate = 2500)
	public void loop() {
		LOGGER.info("start loop");
		List<ActionOnPawn> actionOnPawnList = actionOnPawnService.getAll();
		List<Pawn> pawnList = actionOnPawnList
				.stream()
				.map(ActionOnPawn::execute)
				.distinct()
				.peek(pawn -> pawn.setVersion(pawn.getVersion() + 1))
				.collect(Collectors.toList());

		actionOnPawnService.updateAll(actionOnPawnList);

		simpMessagingTemplate.convertAndSend("/app/world", pawnService.saveAll(pawnList));
	}
}

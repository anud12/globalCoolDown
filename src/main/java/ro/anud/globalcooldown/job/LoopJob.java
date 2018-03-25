package ro.anud.globalcooldown.job;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.mapper.PawnMapper;
import ro.anud.globalcooldown.service.ActionService;
import ro.anud.globalcooldown.service.EffectOnPawnService;
import ro.anud.globalcooldown.service.PawnService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoopJob {

    private static Logger LOGGER = LoggerFactory.getLogger(LoopJob.class);
    private PawnService pawnService;
    private EffectOnPawnService effectOnPawnService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private ActionService actionService;


    @Scheduled(fixedRate = 100)
    public void loop() {

        List<EffectOnPawn> effectOnPawnList = effectOnPawnService.getAll();
        List<Pawn> pawnList = effectOnPawnList.stream()
                .filter(EffectOnPawn::isExecutable)
                .sorted(Comparator.comparing(EffectOnPawn::getPriority))
                .map(EffectOnPawn::execute)
                .distinct()
                .peek(pawn -> pawn.setVersion(pawn.getVersion() + 1))
                .collect(Collectors.toList());

        effectOnPawnService.updateAll(effectOnPawnList);
        actionService.updateAll();

        simpMessagingTemplate.convertAndSend("/app/world", pawnService.saveAll(pawnList)
                .stream()
                .map(PawnMapper::toPawnOutputModel)
                .collect(Collectors.toList()));
    }
}

package ro.anud.globalcooldown.job;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.publisher.PawnPublisher;
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
    private PawnPublisher pawnPublisher;
    private ActionService actionService;


    @Scheduled(fixedRate = 25)
    public void loop() {

        List<EffectOnPawn> effectOnPawnList = effectOnPawnService.getAll();
        List<Pawn> pawnList = effectOnPawnList.stream()
                .peek(EffectOnPawn::incrementAge)
                .filter(EffectOnPawn::isExecutable)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(EffectOnPawn::getPriority)
                                .thenComparing(Comparator.comparing(EffectOnPawn::getAge)
                                                       .reversed()))
                .peek(EffectOnPawn::resetAge)
                .map(EffectOnPawn::execute)
                .distinct()
                .peek(pawn -> pawn.setVersion(pawn.getVersion() + 1))
                .collect(Collectors.toList());

        effectOnPawnService.updateAll(effectOnPawnList);
        actionService.updateAll();
        pawnPublisher.publish(pawnService.saveAll(pawnList));
    }
}

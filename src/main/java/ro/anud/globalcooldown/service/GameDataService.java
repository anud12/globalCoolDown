package ro.anud.globalcooldown.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Getter
public class GameDataService {

    private AreaService areaService;
    private ActionService actionService;
    private PawnService pawnService;
    private EffectOnPawnService effectOnPawnService;


    public GameDataService(final AreaService areaService,
                           final ActionService actionService,
                           final PawnService pawnService,
                           final EffectOnPawnService effectOnPawnService) {
        this.areaService = Objects.requireNonNull(areaService, "areaService must not be null");
        this.actionService = Objects.requireNonNull(actionService, "actionService must not be null");
        this.pawnService = Objects.requireNonNull(pawnService, "pawnService must not be null");
        this.effectOnPawnService = Objects.requireNonNull(effectOnPawnService, "effectOnPawnService must not be null");
    }
}

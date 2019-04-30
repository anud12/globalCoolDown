package ro.anud.globalCooldown.engine.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.engine.factory.CommandFactory;
import ro.anud.globalCooldown.engine.factory.TriggerFactory;
import ro.anud.globalCooldown.data.service.WorldService;
import ro.anud.globalCooldown.api.validation.optionalValidation.OptionalValidation;

@Getter
@Builder
public class CommandScope {
    private OptionalValidation optionalValidation;
    private CommandFactory commandFactory;
    private TriggerFactory triggerFactory;
    private WorldService worldService;
    private Long deltaTime;


}

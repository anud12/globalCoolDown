package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.factory.CommandFactory;
import ro.anud.globalCooldown.factory.TriggerFactory;
import ro.anud.globalCooldown.service.WorldService;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

@Getter
@Builder
public class CommandScope {
    private OptionalValidation optionalValidation;
    private CommandFactory commandFactory;
    private TriggerFactory triggerFactory;
    private WorldService worldService;
    private Long deltaTime;


}

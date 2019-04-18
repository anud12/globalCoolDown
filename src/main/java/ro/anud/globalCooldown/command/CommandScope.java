package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.builder.CommandBuilder;
import ro.anud.globalCooldown.builder.TriggerBuilder;
import ro.anud.globalCooldown.service.WorldService;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

@Getter
@Builder
public class CommandScope {
    private OptionalValidation optionalValidation;
    private CommandBuilder commandBuilder;
    private TriggerBuilder triggerBuilder;
    private WorldService worldService;
    private Long deltaTime;


}

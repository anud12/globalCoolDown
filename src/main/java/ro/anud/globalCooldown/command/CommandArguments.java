package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.builder.CommandBuilder;
import ro.anud.globalCooldown.builder.TriggerBuilder;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

@Getter
@Builder
public class CommandArguments {
    private GameObjectModel gameObjectModel;
    private OptionalValidation optionalValidation;
    private CommandBuilder commandBuilder;
    private TriggerBuilder triggerBuilder;
    private Long deltaTime;


}

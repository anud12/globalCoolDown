package ro.anud.globalCooldown.engine.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.Properties;
import ro.anud.globalCooldown.api.validation.optionalValidation.OptionalValidation;
import ro.anud.globalCooldown.data.factory.GameObjectFactory;
import ro.anud.globalCooldown.data.service.WorldService;
import ro.anud.globalCooldown.engine.factory.CommandFactory;
import ro.anud.globalCooldown.engine.factory.TriggerFactory;

@Getter
@Builder
public class CommandScope {
    private OptionalValidation optionalValidation;
    private CommandFactory commandFactory;
    private TriggerFactory triggerFactory;
    private GameObjectFactory gameObjectFactory;
    private WorldService worldService;
    private Properties properties;


}

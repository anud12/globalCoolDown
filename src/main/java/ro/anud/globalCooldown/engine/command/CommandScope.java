package ro.anud.globalCooldown.engine.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.Properties;
import ro.anud.globalCooldown.api.validation.optionalValidation.OptionalValidation;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.service.WorldService;

@Getter
@Builder
public class CommandScope {
    private OptionalValidation optionalValidation;
    private GameObjectRepository gameObjectRepository;
    private WorldService worldService;
    private Properties properties;
}

package ro.anud.globalCooldown.api.validator;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.api.config.websocket.WebsocketSessionAtributes;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.model.UserModel;
import ro.anud.globalCooldown.api.service.UserService;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.api.validation.validationChain.ValidationChainResult;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CommandValidator {
    private final UserService userService;

    public CommandValidator(final UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    public Function<GameObjectModel, Optional<ValidationChainResult>> isMessageOwnerOfGameObject(final SimpMessageHeaderAccessor inHeaderAccessor) {
        return gameObjectModel -> {
            Optional<OwnerTrait> ownerTrait = gameObjectModel.getTrait(OwnerTrait.class);
            String connectionId = (String) inHeaderAccessor.getSessionAttributes()
                    .get(WebsocketSessionAtributes.CONNECTION_ID.getKey());
            Optional<String> username = userService.getUsernameFromConnectionId(connectionId)
                    .map(UserModel::getUsername);

            boolean result = ownerTrait.isPresent()
                    && username.isPresent()
                    && ownerTrait.get()
                    .getOwnerId()
                    .equals(username.get());

            if (!result) {
                return Optional.of(ValidationChainResult.builder()
                                           .errorCode("Permission denied")
                                           .field("User")
                                           .build());
            }
            return Optional.empty();
        };
    }
}

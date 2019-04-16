package ro.anud.globalCooldown.validator;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.config.websocket.WebsocketSessionAtributes;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.service.UserService;
import ro.anud.globalCooldown.trait.OwnerTrait;
import ro.anud.globalCooldown.validation.validationChain.ValidationChainResult;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CommandValidator {
    private final UserService userService;

    public CommandValidator(final UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    public Function<GameObjectModel, Optional<ValidationChainResult>> gameObjectIsId(final SimpMessageHeaderAccessor inHeaderAccessor) {
        return gameObjectModel -> {
            Optional<OwnerTrait> ownerTrait = gameObjectModel.getTrait(OwnerTrait.class);
            String connectionId = (String) inHeaderAccessor.getSessionAttributes()
                    .get(WebsocketSessionAtributes.CONNECTION_ID.getKey());
            Optional<String> username = userService.getUsernameFromConnectionId(connectionId)
                    .map(UserModel::getUsername);

            System.out.println(username + ":" + ownerTrait);

            boolean result = ownerTrait.isPresent()
                    && username.isPresent()
                    && ownerTrait.get()
                    .getOwnerId()
                    .equals(username.get());
            System.out.println(result);
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

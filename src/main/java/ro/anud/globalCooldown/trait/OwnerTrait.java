package ro.anud.globalCooldown.trait;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OwnerTrait implements Trait {
    private String ownerId;
}

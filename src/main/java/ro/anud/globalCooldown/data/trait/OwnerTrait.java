package ro.anud.globalCooldown.data.trait;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OwnerTrait implements Trait {
    private String ownerId;
}

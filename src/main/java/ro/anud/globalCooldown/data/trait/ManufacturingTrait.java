package ro.anud.globalCooldown.data.trait;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturingTrait implements Trait {
    private Boolean canCreate;
}

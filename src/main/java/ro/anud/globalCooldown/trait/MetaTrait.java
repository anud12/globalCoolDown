package ro.anud.globalCooldown.trait;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class MetaTrait implements Trait {
    private Long id;
}

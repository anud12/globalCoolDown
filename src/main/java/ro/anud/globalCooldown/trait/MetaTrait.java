package ro.anud.globalCooldown.trait;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class MetaTrait implements Trait {
    private Long id;
}

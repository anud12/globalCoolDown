package ro.anud.globalCooldown.trait;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MetaTrait implements Trait {
    private String name;
    private Long id;
}

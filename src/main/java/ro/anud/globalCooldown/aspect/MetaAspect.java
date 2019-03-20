package ro.anud.globalCooldown.aspect;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MetaAspect {
    private String name;
    private Long id;
}

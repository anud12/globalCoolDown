package ro.anud.globalCooldown.aspect;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationAspect {
    final double x;
    final double y;
}

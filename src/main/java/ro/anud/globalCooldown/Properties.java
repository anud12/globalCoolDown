package ro.anud.globalCooldown;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ro.anud.global-cooldown.properties")
@Getter
@Setter
public class Properties {
    private Long deltaTime;
    private Double epsilon;
}

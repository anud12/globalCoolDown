package ro.anud.globalcooldown.model.action;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionOnPawnOutputModel {
    private Long id;
    private String name;
    private String entityId;
}

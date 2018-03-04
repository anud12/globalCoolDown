package ro.anud.globalcooldown.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionModel {
	private String name;
	private Long id;
}

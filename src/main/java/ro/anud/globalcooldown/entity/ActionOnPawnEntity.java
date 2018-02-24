package ro.anud.globalcooldown.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class ActionOnPawnEntity implements Serializable {

	@Id()
	private Long id;

	@JoinTable(name = "action_effect_on_pawn")
	@OneToMany()
	private List<EffectOnPawnEntity> effectOnPawnEntityList;
}

package ro.anud.globalcooldown.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "action_on_pawn")
public class ActionOnPawnEntity implements Serializable {

	@Id()
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_action_on_pawn_gen")
	@SequenceGenerator(name = "seq_action_on_pawn_gen", allocationSize = 1, sequenceName = "seq_action_on_pawn")
	private Long id;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "action_id")
	private List<EffectOnPawnEntity> effectOnPawnEntityList;

	private String saveDateTime;

	@OneToOne
	private ActionOnPawnEntity parent;

	@Override
	public String toString() {
		return "ActionOnPawnEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", parent=" + parent +
				'}';
	}
}

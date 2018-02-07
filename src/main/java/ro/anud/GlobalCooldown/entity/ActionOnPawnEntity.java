package ro.anud.GlobalCooldown.entity;

import lombok.Data;
import lombok.ToString;
import ro.anud.GlobalCooldown.action.ActionOnPawn;

import javax.persistence.*;

@Entity
@Table(name = "action_on_pawn")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString()
public abstract class ActionOnPawnEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_action_on_pawn_gen")
	@SequenceGenerator(name = "seq_action_on_pawn_gen", allocationSize = 1, sequenceName = "seq_action_on_pawn")
	protected Long id;

	@ManyToOne
	@JoinColumn(name = "pawn_id")
	protected Pawn pawn;
	protected String type;

	public abstract ActionOnPawn toAction();
}

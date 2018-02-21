package ro.anud.globalcooldown.entity;

import lombok.*;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.mapper.MoveOnPawnMapper;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "move_on_pawn")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MoveOnPawnEntity extends EffectOnPawnEntity {

	private Long x;
	private Long y;

	@Builder
	private MoveOnPawnEntity(final Long id,
							 final Pawn pawn,
							 final String type,
							 final Long x,
							 final Long y) {
		this.id = id;
		this.pawn = pawn;
		this.type = type;
		this.x = x;
		this.y = y;
	}

	@Override
	public EffectOnPawn toAction() {
		return MoveOnPawnMapper.toModel(this);
	}
}

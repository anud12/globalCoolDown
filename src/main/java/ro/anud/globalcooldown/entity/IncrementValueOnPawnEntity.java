package ro.anud.globalcooldown.entity;

import lombok.*;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.mapper.IncrementValueOnPawnMapper;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "increment_value_on_pawn")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class IncrementValueOnPawnEntity extends EffectOnPawnEntity {
	private int duration;

	@Builder
	private IncrementValueOnPawnEntity(final Long id,
									   final Pawn pawn,
									   final String type,
									   final int duration,
									   final ActionOnPawnEntity actionOnPawnEntity) {
		this.id = id;
		this.pawn = pawn;
		this.type = type;
		this.duration = duration;
		this.action = actionOnPawnEntity;
	}

	@Override
	public EffectOnPawn toAction() {
		return IncrementValueOnPawnMapper.toAction(this);
	}
}

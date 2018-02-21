package ro.anud.GlobalCooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.GlobalCooldown.entity.EffectOnPawnEntity;
import ro.anud.GlobalCooldown.mapper.IncrementValueOnPawnMapper;

@Getter
@Setter
@NoArgsConstructor
public class IncrementValueOnPawnModel implements EffectOnPawnModel {

	protected Long pawnId;
	private int duration;

	@Override
	public EffectOnPawnEntity toEntity() {
		return IncrementValueOnPawnMapper.toEntity(this);
	}
}

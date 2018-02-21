package ro.anud.globalcooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.mapper.IncrementValueOnPawnMapper;

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

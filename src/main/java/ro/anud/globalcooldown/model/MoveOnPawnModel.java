package ro.anud.globalcooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.mapper.MoveOnPawnMapper;

@Getter
@Setter
@NoArgsConstructor
public class MoveOnPawnModel implements EffectOnPawnModel {
	private Long pawnId;
	private Long x;
	private Long y;

	@Override
	public EffectOnPawnEntity toEntity() {
		return MoveOnPawnMapper.toEntity(this);
	}
}

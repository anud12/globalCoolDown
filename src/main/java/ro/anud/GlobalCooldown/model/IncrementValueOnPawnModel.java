package ro.anud.GlobalCooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.GlobalCooldown.entity.ActionOnPawnEntity;
import ro.anud.GlobalCooldown.mapper.IncrementValueOnPawnMapper;

@Getter
@Setter
@NoArgsConstructor
public class IncrementValueOnPawnModel implements ActionOnPawnModel {

	protected Long pawnId;
	private int duration;

	@Override
	public ActionOnPawnEntity toEntity() {
		return IncrementValueOnPawnMapper.toEntity(this);
	}
}

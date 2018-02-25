package ro.anud.globalcooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.mapper.IncrementValueOnPawnMapper;

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

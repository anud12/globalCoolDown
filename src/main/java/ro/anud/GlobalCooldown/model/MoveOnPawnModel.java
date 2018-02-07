package ro.anud.GlobalCooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.GlobalCooldown.action.MoveOnPawn;
import ro.anud.GlobalCooldown.entity.ActionOnPawnEntity;
import ro.anud.GlobalCooldown.mapper.MoveOnPawnMapper;

@Getter
@Setter
@NoArgsConstructor
public class MoveOnPawnModel implements ActionOnPawnModel{
	private Long pawnId;
	private Long x;
	private Long y;

	@Override
	public ActionOnPawnEntity toEntity() {
		return MoveOnPawnMapper.toEntity(this);
	}
}

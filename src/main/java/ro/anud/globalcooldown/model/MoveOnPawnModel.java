package ro.anud.globalcooldown.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;

@Getter
@Setter
@NoArgsConstructor
public class MoveOnPawnModel implements ActionOnPawnModel {
	private Long pawnId;
	private Long x;
	private Long y;

	@Override
	public ActionOnPawnEntity toEntity() {
		/*return MoveOnPawnMapper.toEntity(this);*/
		return ActionOnPawnEntity.builder().build();
	}
}

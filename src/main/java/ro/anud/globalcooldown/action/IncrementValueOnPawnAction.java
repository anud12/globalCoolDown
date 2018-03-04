package ro.anud.globalcooldown.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.IncrementValueOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class IncrementValueOnPawnAction implements ActionOnPawn {

	public static final String NAME = "INCREMENT_VALUE";
	protected Long pawnId;
	private int duration;

	@Override
	public ActionOnPawnEntity toEntity() {
		ArrayList<EffectOnPawnEntity> effectOnPawnEntityArrayList = new ArrayList<>();
		ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
				.name(IncrementValueOnPawn.NAME)
				.effectOnPawnEntityList(effectOnPawnEntityArrayList)
				.build();

		effectOnPawnEntityArrayList.add(IncrementValueOnPawnEntity
				.builder()
				.type(IncrementValueOnPawn.NAME)
				.duration(this.getDuration())
				.pawn(Pawn.builder()
						.id(this.getPawnId())
						.build())
				.actionOnPawnEntity(actionOnPawnEntity)
				.build()
		);
		return actionOnPawnEntity;
	}
}

package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.IncrementValueOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.model.IncrementValueOnPawnModel;

import java.util.ArrayList;

public class IncrementValueOnPawnMapper {
	private IncrementValueOnPawnMapper() {
	}

	public static IncrementValueOnPawn toAction(IncrementValueOnPawnEntity incrementValueOnPawnEntity) {
		return IncrementValueOnPawn.builder()
				.id(incrementValueOnPawnEntity.getId())
				.duration(incrementValueOnPawnEntity.getDuration())
				.pawn(incrementValueOnPawnEntity.getPawn())
				.build();
	}

	public static IncrementValueOnPawnEntity toEntity(IncrementValueOnPawn incrementValueAction) {
		return IncrementValueOnPawnEntity.builder()
				.id(incrementValueAction.getId())
				.type(IncrementValueOnPawn.NAME)
				.duration(incrementValueAction.getDuration())
				.pawn(incrementValueAction.getPawn())
				.build();
	}

	public static ActionOnPawnEntity toEntity(IncrementValueOnPawnModel incrementValueOnPawnModel) {
		ArrayList<EffectOnPawnEntity> effectOnPawnEntityArrayList = new ArrayList<>();
		ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
				.name(IncrementValueOnPawn.NAME)
				.effectOnPawnEntityList(effectOnPawnEntityArrayList)
				.build();

		effectOnPawnEntityArrayList.add(IncrementValueOnPawnEntity
				.builder()
				.type(IncrementValueOnPawn.NAME)
				.duration(incrementValueOnPawnModel.getDuration())
				.pawn(Pawn.builder()
						.id(incrementValueOnPawnModel.getPawnId())
						.build())
				.actionOnPawnEntity(actionOnPawnEntity)
				.build()
		);
		return actionOnPawnEntity;

	}

}

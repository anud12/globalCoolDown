package ro.anud.GlobalCooldown.mapper;

import ro.anud.GlobalCooldown.action.IncrementValueOnPawn;
import ro.anud.GlobalCooldown.entity.IncrementValueOnPawnEntity;
import ro.anud.GlobalCooldown.entity.Pawn;
import ro.anud.GlobalCooldown.model.IncrementValueOnPawnModel;

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

	public static IncrementValueOnPawnEntity toEntity(IncrementValueOnPawnModel incrementValueOnPawnModel) {
		return IncrementValueOnPawnEntity.builder()
				.type(IncrementValueOnPawn.NAME)
				.duration(incrementValueOnPawnModel.getDuration())
				.pawn(Pawn.builder()
						.id(incrementValueOnPawnModel.getPawnId())
						.build())
				.build();
	}

}

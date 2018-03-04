package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.entity.IncrementValueOnPawnEntity;

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

}

package ro.anud.GlobalCooldown.mapper;

import ro.anud.GlobalCooldown.effects.MoveOnPawn;
import ro.anud.GlobalCooldown.entity.MoveOnPawnEntity;
import ro.anud.GlobalCooldown.entity.Pawn;
import ro.anud.GlobalCooldown.geometry.Point;
import ro.anud.GlobalCooldown.model.MoveOnPawnModel;

public class MoveOnPawnMapper {
	private MoveOnPawnMapper() {
	}

	public static MoveOnPawn toModel(MoveOnPawnEntity moveOnPawnEntity) {
		return MoveOnPawn.builder()
				.id(moveOnPawnEntity.getId())
				.destination(new Point(moveOnPawnEntity.getX(),
						moveOnPawnEntity.getY())
				)
				.pawn(moveOnPawnEntity.getPawn())
				.build();
	}

	public static MoveOnPawnEntity toEntity(MoveOnPawn moveOnPawn) {
		return MoveOnPawnEntity.builder()
				.id(moveOnPawn.getId())
				.pawn(moveOnPawn.getPawn())
				.type(MoveOnPawn.NAME)
				.x(moveOnPawn.getDestination().getX())
				.y(moveOnPawn.getDestination().getY())
				.build();
	}

	public static MoveOnPawnEntity toEntity(MoveOnPawnModel moveOnPawn) {
		return MoveOnPawnEntity.builder()
				.pawn(Pawn.builder()
						.id(moveOnPawn.getPawnId())
						.build())
				.x(moveOnPawn.getX())
				.y(moveOnPawn.getY())
				.type(MoveOnPawn.NAME)
				.build();
	}
}

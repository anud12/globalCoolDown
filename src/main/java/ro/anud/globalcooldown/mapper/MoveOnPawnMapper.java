package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.effects.MoveOnPawn;
import ro.anud.globalcooldown.entity.MoveOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.model.MoveOnPawnModel;

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

package ro.anud.globalcooldown.effects;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.geometry.Vector;
import ro.anud.globalcooldown.mapper.MoveOnPawnMapper;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.MOVEMENT;

@Getter
@ToString(callSuper = true)
public class MoveOnPawn implements EffectOnPawn {
	public static final String NAME = "MOVE_ACTION";
	private static final Logger LOGGER = LoggerFactory.getLogger(MoveOnPawn.class);

	private Long id;
	protected Pawn pawn;
	private boolean arrived;
	private Point destination;

	@Builder
	public MoveOnPawn(Long id,
					  Pawn pawn,
					  Point destination) {
		this.id = id;
		this.pawn = pawn;
		this.destination = destination;
		arrived = false;
	}

	@Override
	public Pawn execute() {
		LOGGER.info("for " + pawn.toString());
		arrived = pawn.getPoint().distance(destination) < pawn.getSpeed();
		if (arrived) {
			return pawn.streamSetPoint(destination);
		}
		return pawn.streamSetPoint(calculateNewPosition());
	}

	@Override
	public boolean isArrived() {
		return arrived;
	}

	@Override
	public EffectOnPawnPriority getPriority() {
		return MOVEMENT;
	}

	@Override
	public EffectOnPawnEntity toEntity() {
		return MoveOnPawnMapper.toEntity(this);
	}

	private Point calculateNewPosition() {
		Vector normalized = Vector.normalized(destination.duplicate().streamSubstract(pawn.getPoint()));
		return pawn.getPoint()
				.duplicate()
				.streamTranspose(normalized, pawn.getSpeed());
	}
}

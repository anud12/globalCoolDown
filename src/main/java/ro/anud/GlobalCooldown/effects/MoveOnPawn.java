package ro.anud.GlobalCooldown.effects;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.GlobalCooldown.entity.EffectOnPawnEntity;
import ro.anud.GlobalCooldown.entity.Pawn;
import ro.anud.GlobalCooldown.geometry.Point;
import ro.anud.GlobalCooldown.geometry.Vector;
import ro.anud.GlobalCooldown.mapper.MoveOnPawnMapper;

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

package ro.anud.globalcooldown.effects;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.mapper.IncrementValueOnPawnMapper;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.ADDITION;

@Getter
@ToString
public class IncrementValueOnPawn implements EffectOnPawn {
	public static final String NAME = "INCREMENT_VALUE";
	private static final Logger LOGGER = LoggerFactory.getLogger(IncrementValueOnPawn.class);

	private Long id;
	protected Pawn pawn;
	private int duration;
	private boolean completed;

	@Builder
	private IncrementValueOnPawn(Long id, Pawn pawn, int duration) {
		this.id = id;
		this.pawn = pawn;
		this.duration = duration;
		this.completed = false;
	}

	@Override
	public Pawn execute() {
		LOGGER.info("for " + pawn.toString());
		duration--;
		if (duration <= 0) {
			completed = true;
		}
		return pawn.streamSetValue(pawn.getValue() + 1);
	}

	@Override
	public boolean isArrived() {
		return completed;
	}

	@Override
	public EffectOnPawnPriority getPriority() {
		return ADDITION;
	}

	@Override
	public EffectOnPawnEntity toEntity() {
		return IncrementValueOnPawnMapper.toEntity(this);
	}
}

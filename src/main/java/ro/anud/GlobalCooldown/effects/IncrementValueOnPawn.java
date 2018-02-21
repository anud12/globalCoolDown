package ro.anud.GlobalCooldown.effects;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.GlobalCooldown.entity.EffectOnPawnEntity;
import ro.anud.GlobalCooldown.entity.Pawn;
import ro.anud.GlobalCooldown.mapper.IncrementValueOnPawnMapper;

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
		LOGGER.info("execute for " + pawn.toString());
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
	public EffectOnPawnEntity toEntity() {
		return IncrementValueOnPawnMapper.toEntity(this);
	}
}

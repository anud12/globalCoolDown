package ro.anud.GlobalCooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.GlobalCooldown.action.IncrementValueOnPawn;
import ro.anud.GlobalCooldown.entity.ActionOnPawnEntity;
import ro.anud.GlobalCooldown.entity.IncrementValueOnPawnEntity;

import java.util.List;
import java.util.stream.Stream;

public interface ActionOnPawnRepository extends JpaRepository<ActionOnPawnEntity, Long> {

	List<ActionOnPawnEntity> findByPawnId(final Long id);
	Stream<ActionOnPawnEntity> streamAllByPawnId(final Long pawnId);
}

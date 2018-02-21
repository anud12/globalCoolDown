package ro.anud.GlobalCooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.GlobalCooldown.entity.EffectOnPawnEntity;

import java.util.List;
import java.util.stream.Stream;

public interface EffectOnPawnRepository extends JpaRepository<EffectOnPawnEntity, Long> {

	List<EffectOnPawnEntity> findByPawnId(final Long id);
	Stream<EffectOnPawnEntity> streamAllByPawnId(final Long pawnId);
}

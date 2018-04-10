package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.List;
import java.util.Optional;

public interface PawnRepository extends JpaRepository<Pawn, Long> {
	List<Pawn> findAllByOrderByIdAsc();
	Optional<Pawn> findOneById(Long id);
}

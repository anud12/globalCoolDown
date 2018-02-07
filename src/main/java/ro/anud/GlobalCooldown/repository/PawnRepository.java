package ro.anud.GlobalCooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.GlobalCooldown.entity.Pawn;

import java.util.List;

public interface PawnRepository extends JpaRepository<Pawn, Long> {
	List<Pawn> findAllByOrderByIdAsc();
}

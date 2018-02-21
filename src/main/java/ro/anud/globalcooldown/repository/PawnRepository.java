package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.List;

public interface PawnRepository extends JpaRepository<Pawn, Long> {
	List<Pawn> findAllByOrderByIdAsc();
}

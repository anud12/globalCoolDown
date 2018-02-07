package ro.anud.GlobalCooldown.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.anud.GlobalCooldown.entity.Pawn;
import ro.anud.GlobalCooldown.repository.PawnRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PawnService {

	private PawnRepository pawnRepository;

	public List<Pawn> getAll() {
		return pawnRepository.findAllByOrderByIdAsc();
	}

	public List<Pawn> saveAll(List<Pawn> pawnSet) {
		return pawnRepository.save(pawnSet);
	}
}

package ro.anud.globalcooldown.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.repository.ActionOnPawnRepository;
import ro.anud.globalcooldown.repository.EffectOnPawnRepository;
import ro.anud.globalcooldown.repository.PawnRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PawnService {

    private PawnRepository pawnRepository;
    private EffectOnPawnRepository effectOnPawnRepository;
    private ActionOnPawnRepository actionOnPawnRepository;

    public List<Pawn> getAll() {
        return pawnRepository.findAllByOrderByIdAsc();
    }

    public List<Pawn> saveAll(List<Pawn> pawnSet) {
        return pawnRepository.save(pawnSet);
    }

    @Transactional
    public void remove(Pawn pawn) {
        effectOnPawnRepository.removeAllByPawn_Id(pawn.getId());
        actionOnPawnRepository.removeAllByPawnId(pawn.getId());
        pawnRepository.delete(pawn);
    }

    public Pawn save(Pawn pawn) {
        return pawnRepository.save(pawn);
    }

    public Pawn getById(Long id) {
        return pawnRepository.findOne(id);
    }
}

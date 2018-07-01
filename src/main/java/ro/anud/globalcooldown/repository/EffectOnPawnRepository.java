package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;

import java.util.List;

public interface EffectOnPawnRepository extends JpaRepository<EffectOnPawnEntity, Long> {

    void deleteAllByIdIn(List<Long> idList);

    void removeAllByPawn_Id(Long pawnId);
}

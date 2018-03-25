package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;

import java.util.List;

public interface EffectOnPawnRepository extends JpaRepository<EffectOnPawnEntity, Long> {

    void deleteAllByIdIn(List<Long> idList);
}

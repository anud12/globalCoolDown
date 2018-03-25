package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;

import java.util.List;
import java.util.Optional;

public interface ActionOnPawnRepository extends JpaRepository<ActionOnPawnEntity, Long> {

    Optional<ActionOnPawnEntity> findFirstByPawnIdAndNameOrderBySaveDateTimeDesc(Long pawnId, String name);

    List<ActionOnPawnEntity> findAllByParentIn(List<ActionOnPawnEntity> actionOnPawnEntities);
}

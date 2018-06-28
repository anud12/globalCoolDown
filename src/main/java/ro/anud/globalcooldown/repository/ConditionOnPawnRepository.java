package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.effect.ConditionOnPawnEntity;

public interface ConditionOnPawnRepository extends JpaRepository<ConditionOnPawnEntity, Long> {

}

package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;

public interface ActionOnPawnRepository extends JpaRepository<ActionOnPawnEntity, Long>{
}

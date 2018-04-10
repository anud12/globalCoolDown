package ro.anud.globalcooldown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import ro.anud.globalcooldown.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {
    Optional<UserEntity> findOneByUsernameIgnoreCase(String username);
}

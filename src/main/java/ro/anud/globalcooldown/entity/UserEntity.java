package ro.anud.globalcooldown.entity;

import lombok.Data;
import ro.anud.globalcooldown.security.model.SecurityRole;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_gen")
    @SequenceGenerator(name = "seq_user_gen", allocationSize = 1, sequenceName = "seq_user")
    private Long id;
    private String username;

    @Enumerated(EnumType.STRING)
    private SecurityRole securityRole;
}

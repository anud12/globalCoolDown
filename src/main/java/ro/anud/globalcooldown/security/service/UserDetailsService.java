package ro.anud.globalcooldown.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.repository.UserRepository;
import ro.anud.globalcooldown.security.model.SpringSecurityUser;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(final UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "userRepository must not be null");
    }

    @Override
    public SpringSecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsernameIgnoreCase(username)
                .map(userEntity -> SpringSecurityUser
                        .builder()
                        .id(userEntity.getId())
                        .username(userEntity.getUsername())
                        .password(userEntity.getUsername())
                        .authorities(
                                Collections.singletonList(userEntity.getSecurityRole()))
                        .build())
                .orElse(null);
    }
}

package ro.anud.globalCooldown.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.UserModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final Map<String, UserModel> userModelMap;


    public MyUserDetailsService() {
        userModelMap = new HashMap<>();
    }

    public void addUser(UserModel userModel) {
        userModelMap.put(userModel.getUsername(), userModel);
    }

    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        System.out.println("Username " + s);
        UserDetails userDetails = userModelMap.get(s);
        System.out.println("user" + userDetails);
        return Optional.ofNullable(userDetails)
                .orElseGet(() -> {
                    return new UserDetails() {
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            return null;
                        }

                        @Override
                        public String getPassword() {
                            return null;
                        }

                        @Override
                        public String getUsername() {
                            return null;
                        }

                        @Override
                        public boolean isAccountNonExpired() {
                            return false;
                        }

                        @Override
                        public boolean isAccountNonLocked() {
                            return false;
                        }

                        @Override
                        public boolean isCredentialsNonExpired() {
                            return false;
                        }

                        @Override
                        public boolean isEnabled() {
                            return false;
                        }
                    };
                });
    }
}

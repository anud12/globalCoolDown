package ro.anud.globalCooldown.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.UserModel;

import java.util.HashMap;
import java.util.Map;


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
        return UserModel.builder().username("Test").password("").build();
    }
}

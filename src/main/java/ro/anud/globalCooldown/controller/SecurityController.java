package ro.anud.globalCooldown.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.service.MyUserDetailsService;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    private final MyUserDetailsService myUserDetailsService;

    public SecurityController(final MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = Objects.requireNonNull(myUserDetailsService, "myUserDetailsService must not be null");
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/register")
    public UserModel userModel(@RequestBody final UserModel userModel) {
        LOGGER.info(userModel.toString());
        myUserDetailsService.addUser(userModel);
        return userModel;
    }

    @GetMapping("/me")
    public String getUserDetails(Principal principal) {
        System.out.println(principal);
        return "principal";
    }
}

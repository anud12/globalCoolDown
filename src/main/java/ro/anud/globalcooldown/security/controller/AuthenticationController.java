package ro.anud.globalcooldown.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalcooldown.security.TokenUtils;
import ro.anud.globalcooldown.security.model.SecurityRole;
import ro.anud.globalcooldown.security.model.SpringSecurityUser;
import ro.anud.globalcooldown.security.resource.LoginModel;
import ro.anud.globalcooldown.security.service.UserDetailsService;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.singletonMap;

@Controller
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtils;

    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final UserDetailsService userDetailsService,
                                    final TokenUtils tokenUtils) {
        this.authenticationManager = Objects.requireNonNull(authenticationManager,
                                                            "authenticationManager must not be null");
        this.userDetailsService = Objects.requireNonNull(userDetailsService, "userDetailsService must not be null");
        this.tokenUtils = Objects.requireNonNull(tokenUtils, "tokenUtils must not be null");
    }

    @PostMapping("/auth")
    @CrossOrigin()
    public ResponseEntity getAuth(@RequestBody final LoginModel authenticationRequest) {
        System.out.println(authenticationRequest);

        SpringSecurityUser userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (Objects.nonNull(userDetails)) {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getUsername()
                    ));
            /*
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                                                    null,
                                                                                    AuthorityUtils.createAuthorityList(
                                                                                            SecurityRole.USER.getAuthority()));*/
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenUtils.generateToken(userDetails);
            return ResponseEntity.ok(singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(singletonMap("error", "Username not found"));
        }
    }
}

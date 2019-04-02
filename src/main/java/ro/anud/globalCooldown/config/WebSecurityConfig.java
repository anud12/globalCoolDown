package ro.anud.globalCooldown.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ro.anud.globalCooldown.service.MyUserDetailsService;

@EnableWebMvc
@Configuration
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable();
        http.cors()
                .disable();
        //                .ignoringAntMatchers("/my-endpoint/**")

        http
                .authorizeRequests()
                .mvcMatchers("/security/register", "/security/login", "/my-endpoint/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        http.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
                return authentication;
            }

            @Override
            public boolean supports(final Class<?> aClass) {
                return true;
            }
        });
        http.formLogin()
                .loginPage("/security/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

}
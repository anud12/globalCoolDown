package ro.anud.globalcooldown.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.anud.globalcooldown.security.TokenUtils;
import ro.anud.globalcooldown.security.filter.AuthenticationTokenFilter;
import ro.anud.globalcooldown.security.model.SecurityRole;
import ro.anud.globalcooldown.security.service.UserDetailsService;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final TokenUtils tokenUtils;

    public WebSecurityConfig(final AuthenticationManagerBuilder authenticationManagerBuilder,
                             final UserDetailsService userDetailsService,
                             final AuthenticationEntryPoint authenticationEntryPoint,
                             final TokenUtils tokenUtils) {
        this.authenticationManagerBuilder = Objects.requireNonNull(authenticationManagerBuilder,
                                                                   "authenticationManagerBuilder must not be null");
        this.userDetailsService = Objects.requireNonNull(userDetailsService, "userDetailsService must not be null");
        this.authenticationEntryPoint = Objects.requireNonNull(authenticationEntryPoint,
                                                               "authenticationEntryPoint must not be null");
        this.tokenUtils = Objects.requireNonNull(tokenUtils, "tokenUtils must not be null");
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.html").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/socket").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().hasAnyAuthority(SecurityRole.USER.getAuthority());
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter(tokenUtils,
                                                                                            userDetailsService);
        authenticationTokenFilter.setAuthenticationManager(super.authenticationManagerBean());
        return authenticationTokenFilter;
    }
}

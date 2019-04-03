package ro.anud.globalCooldown.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
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
                .ignoringAntMatchers("/**")
                .disable()
                .cors()
                .disable();

        http
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .authorizeRequests()
                .mvcMatchers("/security/register", "/login", "/my-endpoint/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        http.formLogin()
                //                .loginPage("/security/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationHandler())
                .failureHandler(new AuthenticationHandler());


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
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
        return new PasswordEncoder() {
            @Override
            public String encode(final CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(final CharSequence charSequence, final String s) {
                return true;
            }
        };
    }


}
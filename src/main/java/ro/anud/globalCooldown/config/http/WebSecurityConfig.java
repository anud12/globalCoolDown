package ro.anud.globalCooldown.config.http;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .authorizeRequests()
                .mvcMatchers("/my-endpoint/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
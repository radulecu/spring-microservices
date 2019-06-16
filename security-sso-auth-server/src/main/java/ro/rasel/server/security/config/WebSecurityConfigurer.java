package ro.rasel.server.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize", "/actuator/**")
            .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .permitAll()
            .and().csrf().disable();
    } // @formatter:on

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

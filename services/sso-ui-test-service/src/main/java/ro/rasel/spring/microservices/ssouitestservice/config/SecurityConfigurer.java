package ro.rasel.spring.microservices.ssouitestservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.spring.microservices.component.securityclient.sso.config.IWebSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IWebSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
            .antMatchers("/user").authenticated()
            .antMatchers("/test").permitAll()
            .antMatchers("/test/1").authenticated()
            .antMatchers("/test/admin").hasAnyAuthority("ROLE_ADMIN")
            .antMatchers("/test/user").hasAnyAuthority("INEXISTENT_ROLE_USER").antMatchers("/actuator/**").hasRole("ACTUATOR")
            .anyRequest().authenticated();
        // @formatter:on
    }
}

package ro.rasel.service.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.resource.config.IResourceSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IResourceSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("**")
                .authorizeRequests()
                .regexMatchers("/users/.*").access("#oauth2.hasScope('openid')")
                .antMatchers("/actuator/refresh").hasRole("ACTUATOR")
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().authenticated();
    }
}

package ro.rasel.spring.microservices.echoservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.spring.microservices.component.securityclient.resource.config.IResourceSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IResourceSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .regexMatchers("/test/[a-zA-Z0-9]+").authenticated()
                .anyRequest().authenticated();
    }
}

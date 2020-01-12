package ro.rasel.spring.microservices.service.hystrix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.spring.microservices.component.securityclient.sso.config.IWebSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IWebSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}

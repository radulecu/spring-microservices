package ro.rasel.service.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.resource.IResourceSecurityConfigurer;
import ro.rasel.security.client.sso.IWebSecurityConfigurer;

// iplements both configurers so that the service works no matter which security service is loaded:
// - ResourceSecurityClientComponent.class to be called as proxy
// - WebSecurityClientComponent.class for dirrect authentication and authorization
@Configuration
public class SecurityConfigurer implements IResourceSecurityConfigurer, IWebSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/eureka/**").anonymous()
                .anyRequest().authenticated();
        // @formatter:on
    }
}
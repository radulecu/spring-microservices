package ro.rasel.spring.microservices.eurekaservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import ro.rasel.spring.microservices.component.securityclient.resource.config.IResourceSecurityConfigurer;
import ro.rasel.spring.microservices.component.securityclient.web.config.IWebSecurityConfigurer;

// implements both configurers so that the service works no matter which security service is loaded:
// - ResourceSecurityClientComponent.class to be called as proxy
// - WebSecurityClientComponent.class for direct authentication and authorization
@Configuration
public class SecurityConfigurer implements IResourceSecurityConfigurer, IWebSecurityConfigurer {
    @Override
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> getExpressionInterceptUrlRegistryCustomizer() {
        return auth -> auth
                .regexMatchers("/v2/api-docs").permitAll()
                .antMatchers("/eureka/**").anonymous()
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().authenticated();
    }
}

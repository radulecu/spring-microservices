package ro.rasel.spring.microservices.eurekaservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import ro.rasel.spring.microservices.component.securityclient.basic.config.IBasicSecurityConfigurer;

// implements both configurers so that the service works no matter which security service is loaded:
// - ResourceSecurityClientComponent.class to be called as proxy
// - WebSecurityClientComponent.class for direct authentication and authorization
@Configuration
public class BasicSecurityConfigurer implements IBasicSecurityConfigurer {
    @Override
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> getExpressionInterceptUrlRegistryCustomizer() {
        return auth -> auth
//                .antMatchers("/eureka/js/**").permitAll()
//                .antMatchers("/eureka/css/**").permitAll()
//                .antMatchers("/eureka/apps/**").permitAll()
//                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }
}

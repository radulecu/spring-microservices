package ro.rasel.spring.microservices.gatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import ro.rasel.spring.microservices.component.securityclient.web.config.IWebSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IWebSecurityConfigurer {
    @Override
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> getExpressionInterceptUrlRegistryCustomizer() {
        return auth -> auth
//                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().authenticated();
    }
}

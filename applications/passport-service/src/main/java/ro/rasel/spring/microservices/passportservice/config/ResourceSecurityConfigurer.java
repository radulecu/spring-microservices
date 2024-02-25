package ro.rasel.spring.microservices.passportservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import ro.rasel.spring.microservices.component.securityclient.resource.config.IResourceSecurityConfigurer;

@Configuration
public class ResourceSecurityConfigurer implements IResourceSecurityConfigurer {
    private static final String REST_MATCHER = "/v1(/async)?/users/[^/]*/passport";

    @Override
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> getExpressionInterceptUrlRegistryCustomizer() {
        return auth -> auth
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/v3/api-docs/swagger-config").permitAll()
                .regexMatchers(HttpMethod.GET, REST_MATCHER).access("hasAuthority('SCOPE_read') or hasAuthority('SCOPE_write')")
                .regexMatchers(HttpMethod.POST, REST_MATCHER).access("hasAuthority('SCOPE_write')")
                .regexMatchers(HttpMethod.PUT, REST_MATCHER).access("hasAuthority('SCOPE_write')")
                .regexMatchers(HttpMethod.DELETE, REST_MATCHER).access("hasAuthority('SCOPE_write')")
                .antMatchers("/actuator/health/**").permitAll()
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().denyAll();
    }
}

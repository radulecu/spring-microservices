package ro.rasel.spring.microservices.bookmarkservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import ro.rasel.spring.microservices.component.securityclient.resource.config.IResourceSecurityConfigurer;

@Configuration
public class ResourceSecurityConfigurer implements IResourceSecurityConfigurer {
    private static final String REST_MATCHER = "/v1/users/[^/]*/bookmarks(/[^/]*)?";

    @Override
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> getExpressionInterceptUrlRegistryCustomizer() {
        return auth -> auth
                .regexMatchers("/v2/api-docs").permitAll()
                .regexMatchers(HttpMethod.GET, REST_MATCHER).access("hasAuthority('SCOPE_read') or hasAuthority('SCOPE_write')")
                .regexMatchers(HttpMethod.POST, REST_MATCHER).access("hasAuthority('SCOPE_write')")
                .regexMatchers(HttpMethod.PUT, REST_MATCHER).access("hasAuthority('SCOPE_write')")
                .regexMatchers(HttpMethod.DELETE, REST_MATCHER).access("hasAuthority('SCOPE_write')")
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().denyAll();
    }
}

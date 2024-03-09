package ro.rasel.spring.microservices.contactservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import ro.rasel.spring.microservices.component.securityclient.resource.config.IResourceSecurityConfigurer;

@Configuration
public class ResourceSecurityConfigurer implements IResourceSecurityConfigurer {
    private static final String REST_MATCHER = "/v1/users/[^/]*/contacts(/[^/]*)?";

    @Override
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getAuthorizationManagerRequestMatcherRegistryCustomizer() {
        return auth -> auth
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/v3/api-docs").permitAll()
                .requestMatchers("/v3/api-docs/swagger-config").permitAll()
                .requestMatchers(new RegexRequestMatcher(REST_MATCHER, HttpMethod.GET.name())).hasAnyAuthority("SCOPE_read","SCOPE_write")
                .requestMatchers(new RegexRequestMatcher(REST_MATCHER, HttpMethod.POST.name())).hasAuthority("SCOPE_write")
                .requestMatchers(new RegexRequestMatcher(REST_MATCHER, HttpMethod.PUT.name())).hasAuthority("SCOPE_write")
                .requestMatchers(new RegexRequestMatcher(REST_MATCHER, HttpMethod.DELETE.name())).hasAuthority("SCOPE_write")
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().denyAll();
    }
}

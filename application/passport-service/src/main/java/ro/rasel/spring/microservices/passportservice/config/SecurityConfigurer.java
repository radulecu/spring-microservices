package ro.rasel.spring.microservices.passportservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.spring.microservices.component.securityclient.resource.config.IResourceSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IResourceSecurityConfigurer {
    private static final String REST_MATCHER = "/v1(/async)?/users/[^/]*/passport";
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("**")
                .authorizeRequests()
                .regexMatchers("/v2/api-docs").permitAll()
                .regexMatchers(HttpMethod.GET, REST_MATCHER).access("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.POST,REST_MATCHER).access("#oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.PUT,REST_MATCHER).access("#oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.DELETE,REST_MATCHER).access("#oauth2.hasScope('write')")
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().denyAll();
    }
}

package ro.rasel.spring.microservices.ssoauthservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@Order(-1)//  override the similar AuthorizationServerSecurityConfiguration that has no cors filter
public class OptionsSecurityConfigurer extends AuthorizationServerSecurityConfiguration {
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        super.configure(http);
        http.cors().configurationSource(httpServletRequest-> {
            // this is a general filter
            // for a real application a well defined cors filter should be added with whitelisting
            final CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            corsConfiguration.setAllowCredentials(true);
            return corsConfiguration;
        });
    } // @formatter:on
}

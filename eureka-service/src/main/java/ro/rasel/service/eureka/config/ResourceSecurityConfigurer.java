package ro.rasel.service.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.resource.IResourceSecurityConfigurer;

@Configuration
public class ResourceSecurityConfigurer implements IResourceSecurityConfigurer {
    static final String REGEX_EUREKA_PATTERN = "/|/eureka";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .regexMatcher(REGEX_EUREKA_PATTERN)
            .authorizeRequests()
                .anyRequest().authenticated();
        // @formatter:on
    }
}

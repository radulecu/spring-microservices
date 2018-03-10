package ro.rasel.service.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.sso.IWebSecurityConfigurer;

@Configuration
public class WebSecurityConfigurer implements IWebSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.regexMatcher(regetNot(ResourceSecurityConfigurer.REGEX_EUREKA_PATTERN))
            .authorizeRequests()
                .anyRequest().authenticated();
        // @formatter:on
    }

    private String regetNot(String s) {
        return "(?!" + s + ").*";
    }
}

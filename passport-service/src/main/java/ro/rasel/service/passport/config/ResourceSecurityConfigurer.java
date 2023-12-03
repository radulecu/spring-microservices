package ro.rasel.service.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.resource.IResourceSecurityConfigurer;
import ro.rasel.security.client.sso.IWebSecurityConfigurer;

@Configuration
public class ResourceSecurityConfigurer implements IResourceSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .regexMatcher("/passport/.*")
                .authorizeRequests()
                .regexMatchers("/passport/[a-zA-Z0-9]+").access("#oauth2.hasScope('openid')")
                .anyRequest().authenticated();
    }
}

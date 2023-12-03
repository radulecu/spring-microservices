package ro.rasel.service.contacts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.resource.IResourceSecurityConfigurer;

@Configuration
public class ResourceSecurityConfigurer implements IResourceSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .regexMatchers("/contacts/[a-zA-Z0-9]+").authenticated()
                .anyRequest().authenticated();
    }
}

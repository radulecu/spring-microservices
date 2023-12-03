package ro.rasel.client.server.light.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.sso.IWebSecurityConfigurer;

@Configuration
public class WebSecurityConfigurer implements IWebSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .regexMatchers("/passport/[a-zA-Z0-9]+").authenticated()
//                .regexMatchers("/contacts/[a-zA-Z0-9]+").authenticated()
//                .regexMatchers("/bookmarks/[a-zA-Z0-9]+").authenticated()
                .anyRequest().authenticated();
    }
}

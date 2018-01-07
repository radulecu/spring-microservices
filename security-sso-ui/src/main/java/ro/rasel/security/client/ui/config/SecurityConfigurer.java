package ro.rasel.security.client.ui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import ro.rasel.security.client.sso.IWebSecurityConfigurer;

@Configuration
public class SecurityConfigurer implements IWebSecurityConfigurer {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
            .antMatchers("/user").authenticated()
            .antMatchers("/test").permitAll()
            .antMatchers("/test/1").authenticated()
            .antMatchers("/test/admin").hasAnyAuthority("ROLE_ADMIN")
            .antMatchers("/test/cucurigu").hasAnyAuthority("ROLE_CUCURIGU")
            .anyRequest().denyAll()
        ;
        // @formatter:on
    }
}

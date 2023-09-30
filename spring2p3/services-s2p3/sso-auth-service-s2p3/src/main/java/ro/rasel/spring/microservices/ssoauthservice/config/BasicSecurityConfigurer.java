package ro.rasel.spring.microservices.ssoauthservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ro.rasel.spring.microservices.ssoauthservice.config.properites.SecurityAuthorizationConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@Order(-2000)
public class BasicSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final SecurityAuthorizationConfig securityAuthorizationConfig;

    public BasicSecurityConfigurer(SecurityAuthorizationConfig securityAuthorizationConfig) {
        this.securityAuthorizationConfig = securityAuthorizationConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(
                User.builder()
                        .username(securityAuthorizationConfig.getClientUser())
                        .password(securityAuthorizationConfig.getClientPassword())
                        .roles("user")
                        .build());

        http
                .requestMatchers()
                .antMatchers("/api/publicKey")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().csrf().disable()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(
                            HttpServletRequest httpServletRequest) {
                        final CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                        corsConfiguration.setAllowCredentials(true);
                        return corsConfiguration;
                    }
                })
                .and()
                .userDetailsService(inMemoryUserDetailsManager);
    }
}

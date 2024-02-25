package ro.rasel.spring.microservices.component.securityclient.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.rasel.spring.microservices.component.securityclient.basic.config.properties.BasicSecurityProperties;
import ro.rasel.spring.microservices.component.securityclient.common.config.IHttpSecurityConfigurer;

import java.util.Optional;

@Configuration
public class BasicSecurityConfig {
    private final IBasicSecurityConfigurer basicSecurityConfigurer;
    private final BasicSecurityProperties basicSecurityProperties;

    @Autowired
    public BasicSecurityConfig(
            @Autowired(required = false) IBasicSecurityConfigurer basicSecurityConfigurer,
            BasicSecurityProperties basicSecurityProperties) {
        this.basicSecurityConfigurer = basicSecurityConfigurer;
        this.basicSecurityProperties = basicSecurityProperties;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .antMatcher(basicSecurityProperties.getUrlAntMatcher())
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests(Optional.ofNullable(basicSecurityConfigurer)
                        .map(IHttpSecurityConfigurer::getExpressionInterceptUrlRegistryCustomizer)
                        .orElse(auth -> auth.anyRequest().authenticated()))
                .userDetailsService(s -> {
                    String user = basicSecurityProperties.getUser();
                    if (user != null && user.equals(s)) {
                        return new User(s, basicSecurityProperties.getPassword(), true, true, true, true,
                                AuthorityUtils.createAuthorityList("ROLE_USER"));
                    }
                    return null;
                }).build();
    }

    // This should not be used in production
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }
}
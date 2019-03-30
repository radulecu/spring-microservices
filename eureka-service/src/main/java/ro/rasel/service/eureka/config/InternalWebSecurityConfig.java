package ro.rasel.service.eureka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
@Order(1)
public class InternalWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private EurekaConfigProperties eurekaConfigProperties;

    @Autowired
    public InternalWebSecurityConfig(EurekaConfigProperties eurekaConfigProperties) {
        this.eurekaConfigProperties = eurekaConfigProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().and()
                .antMatcher("/eureka/**")
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(s -> {
                    String user = eurekaConfigProperties.getUser();
                    if (user != null && user.equals(s)) {
                        return new User(s, eurekaConfigProperties.getPassword(), true, true, true, true,
                                AuthorityUtils.createAuthorityList("ROLE_USER"));
                    }
                    return null;
                });
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
package ro.rasel.spring.microservices.component.securityclient.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import ro.rasel.spring.microservices.common.utils.connection.securityclient.SecurityConfig;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@Order(2)
class Oauth2ResourceConfigurer extends WebSecurityConfigurerAdapter {
    private final IResourceSecurityConfigurer configurer;
    private final SecurityConfig securityConfig;

    public Oauth2ResourceConfigurer(
            IResourceSecurityConfigurer configurer,
            SecurityConfig securityConfig) {
        this.configurer = configurer;
        this.securityConfig = securityConfig;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(configurer.getExpressionInterceptUrlRegistryCustomizer())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // this is the symmetric key used in security service
        return NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(securityConfig.getJwtSigningKey().getBytes(), "HMACSHA256")).build();
    }
}
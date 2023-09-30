package ro.rasel.spring.microservices.component.securityclient.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import ro.rasel.spring.microservices.common.utils.properties.securityclient.SecurityConfigProperties;
import ro.rasel.spring.microservices.component.securityclient.common.config.IHttpSecurityConfigurer;
import ro.rasel.spring.microservices.component.securityclient.resource.config.properties.ResourceSecurityProperties;
import ro.rasel.spring.microservices.component.securityclient.resource.service.PublicKeyLoaderService;

import java.util.Optional;

@Configuration
@Order(3)
class Oauth2ResourceConfigurer extends WebSecurityConfigurerAdapter {
    private final IResourceSecurityConfigurer resourceSecurityConfigurer;
    private final ResourceSecurityProperties resourceSecurityProperties;
    private final SecurityConfigProperties securityConfig;

    public Oauth2ResourceConfigurer(
            @Autowired(required = false) IResourceSecurityConfigurer resourceSecurityConfigurer,
            ResourceSecurityProperties resourceSecurityProperties,
            SecurityConfigProperties securityConfig) {
        this.resourceSecurityConfigurer = resourceSecurityConfigurer;
        this.resourceSecurityProperties = resourceSecurityProperties;
        this.securityConfig = securityConfig;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher(resourceSecurityProperties.getUrlAntMatcher())
                .authorizeRequests(Optional.ofNullable(resourceSecurityConfigurer)
                        .map(IHttpSecurityConfigurer::getExpressionInterceptUrlRegistryCustomizer)
                        .orElse(auth -> auth.anyRequest().authenticated()))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    @Bean
    public JwtDecoder jwtDecoder(PublicKeyLoaderService publicKeyLoaderService) {
        // this is the symmetric key used in security service
        return NimbusJwtDecoder
//                .withSecretKey(new SecretKeySpec(securityConfig.getJwtSigningKey().getBytes(), "HMACSHA256")).build();
                .withPublicKey(publicKeyLoaderService.loadSigningKey()).build();
//                .withJwkSetUri("https://localhost:9999/sso/.well-known/openid-configuration").build();
        // spring should have been able to use also to do the same spring.security.oauth2.resourceserver.jwt.issuer-uri=${security.server.url}
    }
}
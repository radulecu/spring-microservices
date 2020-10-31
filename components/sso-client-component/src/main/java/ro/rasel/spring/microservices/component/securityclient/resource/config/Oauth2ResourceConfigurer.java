package ro.rasel.spring.microservices.component.securityclient.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import ro.rasel.spring.microservices.commons.utils.connection.securityclient.SecurityConfig;

@Configuration
@EnableResourceServer
@Order(2)
class Oauth2ResourceConfigurer extends ResourceServerConfigurerAdapter {
    private final IResourceSecurityConfigurer configurer;
    private final SecurityConfig securityConfig;

    public Oauth2ResourceConfigurer(IResourceSecurityConfigurer configurer,
                                    SecurityConfig securityConfig) {
        this.configurer = configurer;
        this.securityConfig = securityConfig;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        configurer.configure(http);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(securityConfig.getJwtSigningKey());
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}
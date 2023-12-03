package ro.rasel.spring.microserivces.component.securityclient.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import ro.rasel.spring.microserivces.component.securityclient.resource.config.connection.ResourceSecurityConfig;

@Configuration
@EnableResourceServer
@Order(2)
class Oauth2ResourceConfigurer extends ResourceServerConfigurerAdapter {
    private final IResourceSecurityConfigurer configurer;
    private final ResourceSecurityConfig resourceSecurityConfig;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public Oauth2ResourceConfigurer(@Autowired IResourceSecurityConfigurer configurer,
                                    ResourceSecurityConfig resourceSecurityConfig) {
        this.configurer = configurer;
        this.resourceSecurityConfig = resourceSecurityConfig;
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
        converter.setSigningKey(resourceSecurityConfig.getJwtSigningKey());
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
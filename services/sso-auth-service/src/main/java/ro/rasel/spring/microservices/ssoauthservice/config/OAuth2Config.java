package ro.rasel.spring.microservices.ssoauthservice.config;

import com.nimbusds.jose.jwk.JWK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.RedirectResolver;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import ro.rasel.spring.microservices.ssoauthservice.config.properites.SecurityAuthorizationConfig;
import ro.rasel.spring.microservices.ssoauthservice.domain.JwtCertificate;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final SecurityAuthorizationConfig securityAuthorizationConfig;
    private final AccessTokenConverter accessTokenConverter;
    private final TokenStore tokenStore;

    @Autowired
    public OAuth2Config(
            AuthenticationManager authenticationManager,
            SecurityAuthorizationConfig securityAuthorizationConfig,
            AccessTokenConverter accessTokenConverter,
            TokenStore tokenStore) {
        this.authenticationManager = authenticationManager;
        this.securityAuthorizationConfig = securityAuthorizationConfig;
        this.accessTokenConverter = accessTokenConverter;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(this.tokenStore)
                .accessTokenConverter(this.accessTokenConverter)
                .redirectResolver(getRedirectResolver());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(securityAuthorizationConfig.getClientUser())
                .secret(securityAuthorizationConfig.getClientPassword())
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .accessTokenValiditySeconds(securityAuthorizationConfig.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(securityAuthorizationConfig.getRefreshTokenValiditySeconds())
                .scopes("read", "write", "openid")
                .autoApprove(true);
    }

    public RedirectResolver getRedirectResolver() {
        return (s, clientDetails) -> s;
    }

    @Bean
    public static TokenStore tokenStore(JwtAccessTokenConverter accessTokenConverter) {
        return new JwtTokenStore(accessTokenConverter);
    }

    @Bean
    public static JwtAccessTokenConverter accessTokenConverter(JwtCertificate jwtCertificate,
                                                               SecurityAuthorizationConfig securityAuthorizationConfig) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        if (securityAuthorizationConfig.isPublicKey()) {
            converter.setKeyPair(jwtCertificate.getKeyPair());
        } else {
            converter.setSigningKey(securityAuthorizationConfig.getSigningKey());
        }
        return converter;
    }

    @Bean
    @Primary
    public static DefaultTokenServices tokenServices(TokenStore tokenStore) {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setRefreshTokenValiditySeconds(Integer.MAX_VALUE);
        return defaultTokenServices;
    }

}

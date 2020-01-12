package ro.rasel.spring.microservices.ssoauthservice.config;

public interface SecurityAuthorizationConfig {
    String getClientUser();

    String getClientPassword();

    String getSigningKey();

    int getAccessTokenValiditySeconds();

    int getRefreshTokenValiditySeconds();
}

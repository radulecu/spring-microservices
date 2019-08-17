package ro.rasel.spring.microserivces.ssoauthservice.config;

public interface SecurityAuthorizationConfig {
    String getClientUser();

    String getClientPassword();

    String getSigningKey();

    int getAccessTokenValiditySeconds();

    int getRefreshTokenValiditySeconds();
}

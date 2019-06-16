package ro.rasel.server.security.config;

public interface SecurityAuthorizationConfig {
    String getClientUser();

    String getClientPassword();

    String getSigningKey();

    int getAccessTokenValiditySeconds();

    int getRefreshTokenValiditySeconds();
}

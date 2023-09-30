package ro.rasel.spring.microservices.ssoauthservice.config.properites;

public interface SecurityAuthorizationConfig {
    String getClientUser();

    String getClientPassword();

    boolean isPublicKey();

    String getSigningKey();

    int getAccessTokenValiditySeconds();

    int getRefreshTokenValiditySeconds();
}

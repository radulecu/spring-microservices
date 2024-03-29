package ro.rasel.spring.microservices.ssoauthservice.config.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.StringJoiner;

@Configuration
@ConfigurationProperties("security.authorization")
class SecurityAuthorizationProperties implements SecurityAuthorizationConfig {
    private String clientUser;
    private String clientPassword;
    private boolean publicKey = false;

    public String issuer;
    private String signingKey;
    private int accessTokenValiditySeconds = 1800;
    private int refreshTokenValiditySeconds = 21600;

    @Override
    public String getClientUser() {
        return clientUser;
    }

    public void setClientUser(String clientUser) {
        this.clientUser = clientUser;
    }

    @Override
    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    @Override
    public boolean isPublicKey() {
        return publicKey;
    }

    public void setPublicKey(boolean publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }


    @Override
    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Override
    public int getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecurityAuthorizationProperties.class.getSimpleName() + "[", "]")
                .add("clientUser='" + clientUser + "'")
                .add("clientPassword='" + clientPassword + "'")
                .add("signingKey='" + signingKey + "'")
                .add("accessTokenValiditySeconds=" + accessTokenValiditySeconds)
                .add("refreshTokenValiditySeconds=" + refreshTokenValiditySeconds)
                .toString();
    }
}

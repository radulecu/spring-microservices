package ro.rasel.server.security.config.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.server.security.config.SecurityAuthorizationConfig;

import java.util.StringJoiner;

@Configuration
@ConfigurationProperties("security.authorization")
class SecurityAuthorizationProperties implements SecurityAuthorizationConfig {
    private String clientUser = "acme";
    private String clientPassword = "acmesecret";
    private String signingKey = "123";

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
    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecurityAuthorizationProperties.class.getSimpleName() + "[", "]")
                .add("clientUser='" + clientUser + "'")
                .add("clientPassword='" + clientPassword + "'")
                .add("signingKey='" + signingKey + "'")
                .toString();
    }
}

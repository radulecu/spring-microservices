package ro.rasel.security.client.sso.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.commons.utils.connection.SecuredConnectionConfigImpl;

@Configuration
@ConfigurationProperties("security.server")
public class OAuth2SecurityConfigImpl extends SecuredConnectionConfigImpl
        implements OAuth2SecurityConfig {
    @Override
    public String toString() {
        return "OAuth2SecurityConfigImpl{} " + super.toString();
    }
}

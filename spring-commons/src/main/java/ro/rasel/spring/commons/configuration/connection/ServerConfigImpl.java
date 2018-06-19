package ro.rasel.spring.commons.configuration.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.commons.utils.connection.ConnectionConfigImpl;

@Configuration
@ConfigurationProperties("server")
public class ServerConfigImpl extends ConnectionConfigImpl
        implements ServerConfig {
    @Override
    public String toString() {
        return "ServerConfigImpl{} " + super.toString();
    }
}

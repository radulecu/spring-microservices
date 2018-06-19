package ro.rasel.eureka.client.component.config.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.commons.utils.connection.SecuredConnectionConfigImpl;

@Configuration
@ConfigurationProperties("registry.server")
public class RegistryConnectionConfigImpl extends SecuredConnectionConfigImpl
        implements RegistryConnectionConfig {
    @Override
    public String toString() {
        return "RegistryConnectionConfigImpl{} " + super.toString();
    }
}

package ro.rasel.spring.microservices.component.eurekaclient.config.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.connection.ConnectionConfigImpl;

@Configuration
@ConfigurationProperties("registry.client")
public class RegistryClientConnectionConfigImpl extends ConnectionConfigImpl
        implements RegistryClientConnectionConfig {
    @Override
    public String toString() {
        return "RegistryConnectionConfigImpl{} " + super.toString();
    }
}

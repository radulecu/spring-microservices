package ro.rasel.spring.microservices.component.eurekaclient.config.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.commons.utils.connection.SecuredConnectionConfigImpl;

@Configuration
@ConfigurationProperties("registry.server")
public class RegistryServerConnectionConfigImpl extends SecuredConnectionConfigImpl
        implements RegistryServerConnectionConfig {
    @Override
    public String toString() {
        return "RegistryConnectionConfigImpl{} " + super.toString();
    }
}

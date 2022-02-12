package ro.rasel.spring.microservices.component.eurekaclient.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.properties.ConnectionConfigPropertiesImpl;

@Configuration
@ConfigurationProperties("registry.client")
public class RegistryClientConnectionConfigPropertiesImpl extends ConnectionConfigPropertiesImpl
        implements RegistryClientConnectionConfigProperties {
    @Override
    public String toString() {
        return "RegistryConnectionConfigImpl{} " + super.toString();
    }
}

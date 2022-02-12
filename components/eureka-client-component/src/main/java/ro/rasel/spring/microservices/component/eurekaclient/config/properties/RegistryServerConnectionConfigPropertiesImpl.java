package ro.rasel.spring.microservices.component.eurekaclient.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.properties.SecuredConnectionConfigPropertiesImpl;

@Configuration
@ConfigurationProperties("registry.server")
public class RegistryServerConnectionConfigPropertiesImpl extends SecuredConnectionConfigPropertiesImpl
        implements RegistryServerConnectionConfigProperties {
    @Override
    public String toString() {
        return "RegistryConnectionConfigImpl{} " + super.toString();
    }
}

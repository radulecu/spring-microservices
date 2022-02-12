package ro.rasel.spring.microservices.springcommon.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.properties.ConnectionConfigPropertiesImpl;

@Configuration
@ConfigurationProperties("server")
public class ServerConfigPropertiesImpl extends ConnectionConfigPropertiesImpl
        implements ServerConfigProperties {
    @Override
    public String toString() {
        return "ServerConfigImpl{} " + super.toString();
    }
}

package ro.rasel.spring.microservices.component.securityclient.resource.config.connection;

import ro.rasel.spring.microservices.commons.utils.connection.ConnectionConfig;

public interface ResourceSecurityConfig extends ConnectionConfig {
    String getJwtSigningKey();
}

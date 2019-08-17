package ro.rasel.spring.microserivces.component.securityclient.resource.config.connection;

import ro.rasel.spring.microserivces.commons.utils.connection.ConnectionConfig;

public interface ResourceSecurityConfig extends ConnectionConfig {
    String getJwtSigningKey();
}

package ro.rasel.security.client.resource.config.connection;

import ro.rasel.commons.utils.connection.ConnectionConfig;

public interface ResourceSecurityConfig extends ConnectionConfig {
    String getJwtSigningKey();
}

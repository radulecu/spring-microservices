package ro.rasel.spring.microservices.common.utils.connection.securityclient;

import ro.rasel.spring.microservices.common.utils.connection.SecuredConnectionConfig;

public interface SecurityConfig extends SecuredConnectionConfig {
    String getJwtSigningKey();

    String getUrl();
}

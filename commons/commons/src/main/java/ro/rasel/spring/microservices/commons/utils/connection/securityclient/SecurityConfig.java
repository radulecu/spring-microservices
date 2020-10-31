package ro.rasel.spring.microservices.commons.utils.connection.securityclient;

import ro.rasel.spring.microservices.commons.utils.connection.SecuredConnectionConfig;

public interface SecurityConfig extends SecuredConnectionConfig {
    String getJwtSigningKey();

    String getUrl();
}

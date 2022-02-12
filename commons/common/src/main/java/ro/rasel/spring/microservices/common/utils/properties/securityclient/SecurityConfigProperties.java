package ro.rasel.spring.microservices.common.utils.properties.securityclient;

import ro.rasel.spring.microservices.common.utils.properties.SecuredConnectionConfigProperties;

public interface SecurityConfigProperties extends SecuredConnectionConfigProperties {
    String getJwtSigningKey();

    String getUrl();
}

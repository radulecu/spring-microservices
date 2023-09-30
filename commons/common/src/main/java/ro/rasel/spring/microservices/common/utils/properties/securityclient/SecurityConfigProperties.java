package ro.rasel.spring.microservices.common.utils.properties.securityclient;

import ro.rasel.spring.microservices.common.utils.properties.SecuredConnectionConfigProperties;

public interface SecurityConfigProperties extends SecuredConnectionConfigProperties {

    boolean isUseSymmetricJwtSigningKey();

    String getJwtSigningKey();

    String getUrl();
}

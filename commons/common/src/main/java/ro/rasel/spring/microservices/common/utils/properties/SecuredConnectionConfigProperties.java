package ro.rasel.spring.microservices.common.utils.properties;

import ro.rasel.spring.microservices.common.utils.properties.ConnectionConfigProperties;

@SuppressWarnings("unused")
public interface SecuredConnectionConfigProperties extends ConnectionConfigProperties {
    String getUser();

    String getPassword();
}

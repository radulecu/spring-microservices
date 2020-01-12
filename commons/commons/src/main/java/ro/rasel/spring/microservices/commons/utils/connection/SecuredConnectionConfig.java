package ro.rasel.spring.microservices.commons.utils.connection;

@SuppressWarnings("unused")
public interface SecuredConnectionConfig extends ConnectionConfig {
    String getUser();

    String getPassword();
}

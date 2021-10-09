package ro.rasel.spring.microservices.common.utils.connection;

@SuppressWarnings("unused")
public interface SecuredConnectionConfig extends ConnectionConfig {
    String getUser();

    String getPassword();
}

package ro.rasel.spring.microserivces.commons.utils.connection;

public interface SecuredConnectionConfig extends ConnectionConfig {
    String getUser();

    String getPassword();
}

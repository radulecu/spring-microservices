package ro.rasel.spring.microserivces.commons.utils.connection;

@SuppressWarnings("unused")
public interface SecuredConnectionConfig extends ConnectionConfig {
    String getUser();

    String getPassword();
}

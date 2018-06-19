package ro.rasel.commons.utils.connection;

public interface SecuredConnectionConfig extends ConnectionConfig {
    String getUser();

    String getPassword();
}

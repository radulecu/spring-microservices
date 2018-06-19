package ro.rasel.commons.utils.connection;

public interface ConnectionConfig {
    String getProtocol();

    String getHostname();

    short getPort();
}

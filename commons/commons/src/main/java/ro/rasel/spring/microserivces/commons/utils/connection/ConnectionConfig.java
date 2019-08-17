package ro.rasel.spring.microserivces.commons.utils.connection;

public interface ConnectionConfig {
    String getProtocol();

    String getHostname();

    short getPort();
}

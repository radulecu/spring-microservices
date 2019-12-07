package ro.rasel.spring.microserivces.commons.utils.connection;

@SuppressWarnings("unused")
public interface ConnectionConfig {
    String getProtocol();

    String getHostname();

    short getPort();
}

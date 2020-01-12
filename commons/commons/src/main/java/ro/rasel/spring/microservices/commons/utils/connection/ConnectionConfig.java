package ro.rasel.spring.microservices.commons.utils.connection;

@SuppressWarnings("unused")
public interface ConnectionConfig {
    String getProtocol();

    String getHostname();

    short getPort();
}

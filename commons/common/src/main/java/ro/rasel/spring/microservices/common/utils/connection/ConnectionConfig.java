package ro.rasel.spring.microservices.common.utils.connection;

@SuppressWarnings("unused")
public interface ConnectionConfig {
    String getProtocol();

    String getHostname();

    short getPort();
}

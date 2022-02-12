package ro.rasel.spring.microservices.common.utils.properties;

@SuppressWarnings("unused")
public interface ConnectionConfigProperties {
    String getProtocol();

    String getHostname();

    short getPort();
}

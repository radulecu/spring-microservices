package ro.rasel.spring.microservices.common.utils.properties;

import java.util.Objects;

@SuppressWarnings("unused")
public class ConnectionConfigPropertiesImpl implements ConnectionConfigProperties {
    private String protocol;
    private String hostname;
    private short port;

    public ConnectionConfigPropertiesImpl() {
    }

    public ConnectionConfigPropertiesImpl(String protocol, String hostname, short port) {
        this.protocol = protocol;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConnectionConfigPropertiesImpl that = (ConnectionConfigPropertiesImpl) o;
        return port == that.port &&
                Objects.equals(protocol, that.protocol) &&
                Objects.equals(hostname, that.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, hostname, port);
    }

    @Override
    public String toString() {
        return "ConnectionConfigImpl{" +
                "protocol='" + protocol + '\'' +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}

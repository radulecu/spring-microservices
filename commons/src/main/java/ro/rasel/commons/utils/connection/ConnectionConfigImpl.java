package ro.rasel.commons.utils.connection;

import java.util.Objects;

public class ConnectionConfigImpl implements ConnectionConfig {
    private String protocol;
    private String hostname;
    private short port;

    public ConnectionConfigImpl(){
    }

    public ConnectionConfigImpl(String protocol, String hostname, short port) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionConfigImpl that = (ConnectionConfigImpl) o;
        return port == that.port &&
                protocol == that.protocol &&
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

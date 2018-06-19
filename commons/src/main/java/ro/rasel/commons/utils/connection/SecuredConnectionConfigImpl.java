package ro.rasel.commons.utils.connection;

import java.util.Objects;

public class SecuredConnectionConfigImpl extends ConnectionConfigImpl
        implements SecuredConnectionConfig {
    private String user;
    private String password;

    public SecuredConnectionConfigImpl() {
    }

    public SecuredConnectionConfigImpl(String protocol, String hostname, short port, String user,
                                       String password) {
        super(protocol, hostname, port);
        this.user = user;
        this.password = password;
    }

    @Override
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SecuredConnectionConfigImpl that = (SecuredConnectionConfigImpl) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, password);
    }

    @Override
    public String toString() {
        return "SecuredConnectionConfigImpl{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}

package ro.rasel.spring.microservices.common.utils.properties;

import java.util.Objects;

@SuppressWarnings("unused")
public class SecuredConnectionConfigPropertiesImpl extends ConnectionConfigPropertiesImpl
        implements SecuredConnectionConfigProperties {
    private String user;
    private String password;

    public SecuredConnectionConfigPropertiesImpl() {
    }

    public SecuredConnectionConfigPropertiesImpl(String protocol, String hostname, short port, String user,
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
        SecuredConnectionConfigPropertiesImpl that = (SecuredConnectionConfigPropertiesImpl) o;
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

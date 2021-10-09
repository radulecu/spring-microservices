package ro.rasel.spring.microservices.component.securityclient.common.config.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.connection.ConnectionConfigImpl;
import ro.rasel.spring.microservices.common.utils.connection.securityclient.SecurityConfig;

import java.util.Objects;
import java.util.StringJoiner;

@Configuration
@ConfigurationProperties("security.server")
public class SecurityConfigImpl extends ConnectionConfigImpl
        implements SecurityConfig {
    private String jwtSigningKey;
    private String user;
    private String password;
    private String url;

    @Override
    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SecurityConfigImpl that = (SecurityConfigImpl) o;
        return Objects.equals(jwtSigningKey, that.jwtSigningKey) &&
                Objects.equals(user, that.user) &&
                Objects.equals(password, that.password) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jwtSigningKey, user, password, url);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecurityConfigImpl.class.getSimpleName() + "[", "]")
                .add("jwtSigningKey='" + jwtSigningKey + "'")
                .add("user='" + user + "'")
                .add("password='" + password + "'")
                .add("url='" + url + "'")
                .toString() + " " + super.toString();
    }
}

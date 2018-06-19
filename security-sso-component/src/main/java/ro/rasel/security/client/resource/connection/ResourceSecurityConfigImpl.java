package ro.rasel.security.client.resource.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.rasel.commons.utils.connection.ConnectionConfigImpl;

import java.util.Objects;

@Configuration
@ConfigurationProperties("security.server")
public class ResourceSecurityConfigImpl extends ConnectionConfigImpl
        implements ResourceSecurityConfig {
    private String jwtSigningKey;

    @Override
    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResourceSecurityConfigImpl that = (ResourceSecurityConfigImpl) o;
        return Objects.equals(jwtSigningKey, that.jwtSigningKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jwtSigningKey);
    }

    @Override
    public String toString() {
        return "ResourceSecurityConfigImpl{" +
                "jwtSigningKey='" + jwtSigningKey + '\'' +
                "} " + super.toString();
    }
}

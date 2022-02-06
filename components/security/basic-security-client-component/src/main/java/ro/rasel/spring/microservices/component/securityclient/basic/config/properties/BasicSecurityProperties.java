package ro.rasel.spring.microservices.component.securityclient.basic.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.StringJoiner;

@Configuration
@ConfigurationProperties("security.basic")
public class BasicSecurityProperties {
    private String user;
    private String password;
    private String urlAntMatcher="/**";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlAntMatcher() {
        return urlAntMatcher;
    }

    public void setUrlAntMatcher(String urlAntMatcher) {
        this.urlAntMatcher = urlAntMatcher;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BasicSecurityProperties.class.getSimpleName() + "[", "]")
                .add("user='" + user + "'")
                .add("password='" + password + "'")
                .add("urlAntMatcher='" + urlAntMatcher + "'")
                .toString();
    }
}

package ro.rasel.spring.microserivces.eurekaservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("eureka")
public class EurekaConfigProperties {
    private String user;
    private String password;

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

    @Override
    public String toString() {
        return "EurekaConfigProperties{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

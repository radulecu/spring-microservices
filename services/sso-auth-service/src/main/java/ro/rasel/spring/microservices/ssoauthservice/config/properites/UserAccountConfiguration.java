package ro.rasel.spring.microservices.ssoauthservice.config.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.StringJoiner;

@Configuration
@ConfigurationProperties("account")
public class UserAccountConfiguration {
    int maximumTries = 7;
    int lockTimeInMinutes = 15;

    public int getMaximumTries() {
        return maximumTries;
    }

    public void setMaximumTries(int maximumTries) {
        this.maximumTries = maximumTries;
    }

    public int getLockTimeInMinutes() {
        return lockTimeInMinutes;
    }

    public void setLockTimeInMinutes(int lockTimeInMinutes) {
        this.lockTimeInMinutes = lockTimeInMinutes;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserAccountConfiguration.class.getSimpleName() + "[", "]")
                .add("maximumTries=" + maximumTries)
                .add("lockTimeInMinutes=" + lockTimeInMinutes)
                .toString();
    }
}

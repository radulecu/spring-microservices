package ro.rasel.spring.microservices.springcommon.config.test.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ConfigurationProperties("test.memory.load")
public class LoadMemoryTestConfigProps {
    private boolean enabled=false;

    /**
     * Max percentage when load is increased
     */
    private int maxPercentage= Integer.MAX_VALUE;

    /**
     * Time to reach 100% memory load
     */
    private int duration=120+new Random().nextInt(60);

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

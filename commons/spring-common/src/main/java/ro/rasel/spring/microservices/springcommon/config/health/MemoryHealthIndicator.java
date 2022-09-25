package ro.rasel.spring.microservices.springcommon.config.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.time.LocalDateTime;

@Configuration()
public class MemoryHealthIndicator implements HealthIndicator, HealthContributor {
    LocalDateTime startUnhealthy = null;

    @Override
    public Health health() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        int mb = 1024 * 1024;
        long xmx = memoryBean.getHeapMemoryUsage().getMax();
        long crt = memoryBean.getHeapMemoryUsage().getUsed();
        double load = (double) crt / xmx;
        boolean healthy = load < 0.98;

        Health.Builder builder;
        if (healthy) {
            startUnhealthy = null;
            builder = Health.up()
                    .withDetail("status", "healthy");
        } else {
            if (startUnhealthy == null) {
                startUnhealthy = LocalDateTime.now();
            }
            // when unhealthy for a long time the we report it as down
            builder = LocalDateTime.now().isBefore(startUnhealthy.plusSeconds(1)) ? Health.up() : Health.down();
            builder.withDetail("status", "unhealthy");
        }

        return builder
                .withDetail("percentage", load * 100)
                .withDetail("current", crt / mb + " MB")
                .withDetail("max", xmx / mb + " MB")
                .build();
    }
}

package ro.rasel.spring.microservices.springcommon.config.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.springcommon.config.test.props.LoadMemoryTestConfigProps;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class used to artificially increase memory used by application to test kubernetes impact
 */
@Configuration
@ConditionalOnProperty(name = "test.memory.load.enabled", havingValue = "true")
public class LoadMemoryTestConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadMemoryTestConfiguration.class);

    private final LoadMemoryTestConfigProps loadMemoryTestConfigProps;

    public LoadMemoryTestConfiguration(LoadMemoryTestConfigProps loadMemoryTestConfigProps) {
        this.loadMemoryTestConfigProps = loadMemoryTestConfigProps;
    }

    @PostConstruct
    public void postConstruct() {
        int start = LocalTime.now().toSecondOfDay();
        int referenceDuration = loadMemoryTestConfigProps.getDuration();

        int mb = 1024 * 1024;
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long xmx = memoryBean.getHeapMemoryUsage().getMax() / mb;
        long xms = memoryBean.getHeapMemoryUsage().getInit() / mb;
        LOGGER.info("Initial Memory (xms) : {}mb", xms);
        LOGGER.info("Max Memory (xmx) : {}mb", xmx);

        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        new Thread(() -> {
            String value = "value";
            for (int i = 0; i < 10; i++) {
                value += value;
            }
            long i = 1024;
            while (true) {
                try {
                    long crt = memoryBean.getHeapMemoryUsage().getUsed() / mb;
                    int duration = LocalTime.now().toSecondOfDay() - start;
                    double expectedLoadFractionByTime = (double) duration / referenceDuration;
                    double expectedLoadByTime = xmx * expectedLoadFractionByTime;
                    if (expectedLoadFractionByTime * 100 < loadMemoryTestConfigProps.getMaxPercentage()) {
                        if (crt < expectedLoadByTime) {
                            set.add(value + i++);
                        } else {
                            LOGGER.info("Current Memory: {}mb/{}mb, time {}s/{}s", crt, xmx, duration, referenceDuration);
                            sleep(1000);
                        }
                        if (i % 1000 == 0) {
                            LOGGER.info("Current Memory: {}mb/{}mb, time {}s/{}s", crt, xmx, duration, referenceDuration);
                        }
                    } else {
                        LOGGER.info("Current Memory: {}mb/{}mb, time {}s/{}s", crt, xmx, duration, referenceDuration);
                        sleep(10000);
                    }
                } catch (Throwable t) {
                    LOGGER.error(t.getMessage(), t);
                }
            }
        }).start();
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

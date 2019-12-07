package ro.rasel.spring.microservices.component.hystrix.config;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microserivces.commons.utils.async.AsynchronousDataProvider;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class HystrixConfig {
    private static final Logger LOG= LoggerFactory.getLogger(HystrixConfig.class);

    private final List<AsynchronousDataProvider> asynchronousDataProviders;

    public HystrixConfig(
            @Autowired(required = false) List<AsynchronousDataProvider> asynchronousDataProviders) {
        this.asynchronousDataProviders = asynchronousDataProviders;
    }

    @PostConstruct
    public void init() {
        // keeps references of existing Hystrix plugins.
        HystrixConcurrencyStrategy existingConcurrencyStrategy = HystrixPlugins.getInstance().getConcurrencyStrategy();
        HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
        // reset the Hystrix plugin
        HystrixPlugins.reset();
        // configure the  plugin
        HystrixPlugins.getInstance().registerConcurrencyStrategy(existingConcurrencyStrategy);
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(new HystrixCommandExecutionHookWrapper(commandExecutionHook,
                asynchronousDataProviders));
        LOG.info("Context propagation enabled for Hystrix.");
    }

}

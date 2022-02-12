package ro.rasel.spring.microservices.component.eurekaclient.config;

import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;

@Configuration
public class EurekaConfiguration {
    @Bean
    public LoadBalancedRetryFactory retryFactory(SpringClientFactory springClientFactory) {
        return new RibbonLoadBalancedRetryFactory(springClientFactory){
            @Override
            public BackOffPolicy createBackOffPolicy(String service) {
                final ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
                exponentialBackOffPolicy.setMultiplier(1);
                return exponentialBackOffPolicy;
            }
        };
    }
}
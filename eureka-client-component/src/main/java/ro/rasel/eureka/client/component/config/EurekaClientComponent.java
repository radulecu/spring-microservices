package ro.rasel.eureka.client.component.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@ConditionalOnProperty(name = "eureka.client.enabled", havingValue = "true")
public class EurekaClientComponent {

}

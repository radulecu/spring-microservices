package ro.rasel.ssl.truststore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.spring.commons.EnableSpringCommonsComponent;

@ConditionalOnProperty(name = "truststore.enabled", havingValue = "true")
@EnableSpringCommonsComponent
@ComponentScan
public class TrustStoreComponent {
}

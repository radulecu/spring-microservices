package ro.rasel.ssl.truststore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.spring.commons.SpringCommonComponent;
import ro.rasel.ssl.truststore.configuration.TrustStoreConfig;

@ConditionalOnProperty(name = "truststore.enabled", havingValue = "true")
@ComponentScan(basePackageClasses = {SpringCommonComponent.class, TrustStoreConfig.class})
public class TrustStoreComponent {
}

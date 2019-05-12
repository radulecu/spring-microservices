package ro.rasel.ssl.keystore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;

@ConditionalOnProperty(name = "truststore.enabled", havingValue = "true", matchIfMissing = true)
@PropertySource("classpath:application-ssl.properties")
public class KeyStoreComponent {
}

package ro.rasel.ssl.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ro.rasel.spring.commons.EnableSpringCommonsComponent;

@ConditionalOnProperty(name = "truststore.enabled", havingValue = "true", matchIfMissing = true)
@EnableSpringCommonsComponent
@ComponentScan
@PropertySource("classpath:application-clientSsl.properties")
public class SslClientComponent {
}

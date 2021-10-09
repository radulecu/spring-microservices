package ro.rasel.spring.microservices.component.ssl.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ro.rasel.spring.microservices.springcommon.EnableSpringCommonsComponent;

@ConditionalOnProperty(name = "client.ssl.enabled", havingValue = "true", matchIfMissing = true)
@EnableSpringCommonsComponent
@ComponentScan
@PropertySource("classpath:application-clientSsl.properties")
public class SslClientComponent {
}

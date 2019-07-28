package ro.rasel.ssl.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;

@ConditionalOnProperty(name = "ssl.enabled", havingValue = "true", matchIfMissing = true)
@PropertySource("classpath:application-serverSsl.properties")
public class SslClientComponent {
}

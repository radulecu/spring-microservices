package ro.rasel.ssl.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.spring.commons.utils.TempFileManager;

@ConditionalOnProperty(value = "truststore.enabled", matchIfMissing = false)
@ComponentScan(basePackageClasses = {TempFileManager.class, TruststoreConfig.class})
public class TruststoreComponent {
}

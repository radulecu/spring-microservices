package ro.rasel.security.client.resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.security.client.configuration.OAuth2FeignAutoConfiguration;
import ro.rasel.spring.commons.utils.TempFileManager;
import ro.rasel.tls.configuration.SSLConfig;

@ConditionalOnProperty(value = "security.enabled", matchIfMissing = true)
@ComponentScan(basePackageClasses = {ResourceSecurity.class, OAuth2FeignAutoConfiguration.class, TempFileManager.class,
        SSLConfig.class})
public class ResourceSecurity {
}
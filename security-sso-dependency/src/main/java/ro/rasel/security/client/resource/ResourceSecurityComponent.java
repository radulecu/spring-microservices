package ro.rasel.security.client.resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.security.client.configuration.OAuth2FeignAutoConfiguration;

@ConditionalOnProperty(value = "security.enabled", matchIfMissing = true)
@ComponentScan(basePackageClasses = {ResourceSecurityComponent.class, OAuth2FeignAutoConfiguration.class})
public class ResourceSecurityComponent {
}
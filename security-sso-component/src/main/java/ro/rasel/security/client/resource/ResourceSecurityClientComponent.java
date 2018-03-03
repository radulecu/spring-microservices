package ro.rasel.security.client.resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ro.rasel.security.client.configuration.OAuth2FeignAutoConfiguration;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
@ComponentScan(basePackageClasses = {ResourceSecurityClientComponent.class, OAuth2FeignAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSecurityClientComponent {
}
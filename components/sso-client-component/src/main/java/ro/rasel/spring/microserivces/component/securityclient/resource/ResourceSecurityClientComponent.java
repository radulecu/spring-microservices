package ro.rasel.spring.microserivces.component.securityclient.resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ro.rasel.spring.microserivces.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.common.config.OAuth2FeignAutoConfiguration;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackageClasses = {ResourceSecurityClientComponent.class, OAuth2FeignAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application-security.properties")
public class ResourceSecurityClientComponent implements SecurityClientComponent {

}
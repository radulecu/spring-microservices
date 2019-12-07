package ro.rasel.spring.microserivces.component.securityclient.resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ro.rasel.spring.microserivces.component.securityclient.common.SecurityClientComponent;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application-security-resource.properties")
public class ResourceSecurityClientComponent implements SecurityClientComponent {

}
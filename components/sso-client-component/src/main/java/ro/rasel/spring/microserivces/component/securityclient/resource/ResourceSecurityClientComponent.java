package ro.rasel.spring.microserivces.component.securityclient.resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ro.rasel.spring.microserivces.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.common.config.async.SecurityAsynchronousDataProviderImpl;
import ro.rasel.spring.microserivces.component.securityclient.common.config.globalmethodsecurity.GlobalMethodSecurityConfig;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackageClasses = {ResourceSecurityClientComponent.class, SecurityAsynchronousDataProviderImpl.class,
        GlobalMethodSecurityConfig.class})
@PropertySource("classpath:application-security-resource.properties")
public class ResourceSecurityClientComponent implements SecurityClientComponent {

}
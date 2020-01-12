package ro.rasel.spring.microservices.component.securityclient.sso;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ro.rasel.spring.microservices.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microservices.component.securityclient.common.config.async.SecurityAsynchronousDataProviderImpl;
import ro.rasel.spring.microservices.component.securityclient.common.config.globalmethodsecurity.GlobalMethodSecurityConfig;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackageClasses = {WebSecurityClientComponent.class, SecurityAsynchronousDataProviderImpl.class,
        GlobalMethodSecurityConfig.class})
@PropertySource("classpath:application-security.properties")
public class WebSecurityClientComponent implements SecurityClientComponent {

}
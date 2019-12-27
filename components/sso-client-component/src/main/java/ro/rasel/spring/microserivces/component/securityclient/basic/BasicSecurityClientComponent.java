package ro.rasel.spring.microserivces.component.securityclient.basic;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.spring.microserivces.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.common.config.globalmethodsecurity.GlobalMethodSecurityConfig;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackageClasses = {BasicSecurityClientComponent.class, GlobalMethodSecurityConfig.class})
public class BasicSecurityClientComponent implements SecurityClientComponent {
}
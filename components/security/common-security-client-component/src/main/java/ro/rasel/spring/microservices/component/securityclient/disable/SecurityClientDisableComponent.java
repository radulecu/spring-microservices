package ro.rasel.spring.microservices.component.securityclient.disable;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ro.rasel.spring.microservices.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microservices.component.securityclient.disable.config.SecurityClientDisableConfigurer;

@Configuration
@Import(SecurityClientDisableConfigurer.class)
@ConditionalOnMissingBean(SecurityClientComponent.class)
public class SecurityClientDisableComponent {
}

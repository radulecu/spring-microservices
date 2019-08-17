package ro.rasel.spring.microserivces.component.securityclient.disable;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ro.rasel.spring.microserivces.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.disable.config.SecurityClientDisableConfigurer;

@Configuration
@Import(SecurityClientDisableConfigurer.class)
@ConditionalOnMissingBean(SecurityClientComponent.class)
public class SecurityClientDisableComponent {
}

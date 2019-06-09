package ro.rasel.security.client.disable;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ro.rasel.security.client.common.SecurityClientComponent;
import ro.rasel.security.client.disable.config.SecurityClientDisableConfigurer;

@Configuration
@Import(SecurityClientDisableConfigurer.class)
@ConditionalOnMissingBean(SecurityClientComponent.class)
public class SecurityClientDisableComponent {
}

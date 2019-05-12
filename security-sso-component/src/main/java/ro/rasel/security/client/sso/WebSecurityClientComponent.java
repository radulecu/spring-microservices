package ro.rasel.security.client.sso;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ro.rasel.security.client.common.config.OAuth2FeignAutoConfiguration;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackageClasses = {WebSecurityClientComponent.class, OAuth2FeignAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application-security.properties")
public class WebSecurityClientComponent {

}
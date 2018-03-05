package ro.rasel.security.client.sso;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ro.rasel.security.client.configuration.OAuth2FeignAutoConfiguration;
import ro.rasel.spring.commons.SpringCommonPackageRoot;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
@ComponentScan(basePackageClasses = {WebSecurityClientComponent.class, OAuth2FeignAutoConfiguration.class,
        SpringCommonPackageRoot.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityClientComponent {

}
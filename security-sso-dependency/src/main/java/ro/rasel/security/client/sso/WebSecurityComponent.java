package ro.rasel.security.client.sso;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.security.client.configuration.OAuth2FeignAutoConfiguration;
import ro.rasel.spring.commons.SpringCommonParent;

@ConditionalOnProperty(value = "security.enabled", matchIfMissing = false)
@ComponentScan(
        basePackageClasses = {WebSecurityComponent.class, OAuth2FeignAutoConfiguration.class, SpringCommonParent.class})
public class WebSecurityComponent {

}
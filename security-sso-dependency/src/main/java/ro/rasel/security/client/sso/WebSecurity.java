package ro.rasel.security.client.sso;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import ro.rasel.security.client.configuration.OAuth2FeignAutoConfiguration;
import ro.rasel.spring.commons.SpringCommonParent;
import ro.rasel.tls.configuration.SSLConfig;

@ConditionalOnProperty(value = "security.enabled", matchIfMissing = true)
@ComponentScan(basePackageClasses = {WebSecurity.class, OAuth2FeignAutoConfiguration.class, SpringCommonParent.class,
        SSLConfig.class})
public class WebSecurity {

}
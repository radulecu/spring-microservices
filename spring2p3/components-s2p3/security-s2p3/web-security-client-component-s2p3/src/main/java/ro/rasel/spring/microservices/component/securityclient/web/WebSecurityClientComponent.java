package ro.rasel.spring.microservices.component.securityclient.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ro.rasel.spring.microservices.component.securityclient.common.SecurityClientComponent;
import ro.rasel.spring.microservices.component.securityclient.common.SecurityCommonComponentScanBaseClass;

@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan
@Import(SecurityCommonComponentScanBaseClass.class)
@PropertySource("classpath:application-security-web.properties")
public class WebSecurityClientComponent implements SecurityClientComponent {

}
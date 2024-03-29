package ro.rasel.spring.microservices.springcommon;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * root class to used for scanning of spring commons bean
 */
@ComponentScan
@PropertySource("classpath:application-common.properties")
public class SpringCommonComponent {
}

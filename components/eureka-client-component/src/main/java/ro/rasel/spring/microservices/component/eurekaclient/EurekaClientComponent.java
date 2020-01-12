package ro.rasel.spring.microservices.component.eurekaclient;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableEurekaClient
@ComponentScan
@PropertySource("classpath:application-eureka.properties")
public class EurekaClientComponent {
}

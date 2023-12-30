package ro.rasel.spring.microservices.component.swagger;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("classpath:application-swagger.properties")
public class SwaggerComponent {
}

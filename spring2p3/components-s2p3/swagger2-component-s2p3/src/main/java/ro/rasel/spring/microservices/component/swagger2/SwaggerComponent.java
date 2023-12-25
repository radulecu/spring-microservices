package ro.rasel.spring.microservices.component.swagger2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan
@PropertySource("classpath:application-swagger.properties")
public class SwaggerComponent {
}

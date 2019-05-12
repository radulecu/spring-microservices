package ro.rasel.swagger;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan
@PropertySource("classpath:application-swagger.properties")
public class SwaggerComponent {
}

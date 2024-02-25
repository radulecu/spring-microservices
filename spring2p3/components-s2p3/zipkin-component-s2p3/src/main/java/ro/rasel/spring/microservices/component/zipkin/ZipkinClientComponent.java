package ro.rasel.spring.microservices.component.zipkin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("classpath:application-zipkin.properties")
public class ZipkinClientComponent  {
}
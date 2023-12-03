package ro.rasel.spring.microservices.resourcegatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;
import ro.rasel.spring.microservices.springcommon.EnableSpringCommonsComponent;

@SpringBootApplication
@EnableSpringCommonsComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableEurekaClientComponent
@EnableZipkinClientComponent
public class ResourceGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceGatewayServiceApplication.class, args);
    }
}

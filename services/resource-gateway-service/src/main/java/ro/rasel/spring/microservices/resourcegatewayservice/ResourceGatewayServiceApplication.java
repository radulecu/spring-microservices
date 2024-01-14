package ro.rasel.spring.microservices.resourcegatewayservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.swagger.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;
import ro.rasel.spring.microservices.springcommon.EnableSpringCommonsComponent;

@SpringBootApplication
@EnableSpringCommonsComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableEurekaClientComponent
@EnableZipkinClientComponent
@EnableSwaggerComponent
@OpenAPIDefinition(info = @Info(title = "Gateway service", description = "Bookmark REST service", version = "v1",
        license = @License(name = "Apache License, Version 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
public class ResourceGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceGatewayServiceApplication.class, args);
    }
}

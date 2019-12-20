package ro.rasel.spring.microserivces.resourcegatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.spring.microserivces.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microserivces.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microserivces.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microserivces.component.swagger.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
@EnableZipkinClientComponent
public class ResourceGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceGatewayServiceApplication.class, args);
    }
}
 
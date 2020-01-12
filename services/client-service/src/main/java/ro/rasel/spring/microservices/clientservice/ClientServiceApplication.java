package ro.rasel.spring.microservices.clientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.securityclient.sso.EnableWebSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.swagger.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.hystrix.EnableHystrixClientComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
@EnableEurekaClientComponent
@EnableHystrixClientComponent
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
}
 
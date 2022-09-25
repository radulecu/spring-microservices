package ro.rasel.spring.microservices.passportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.hystrix.EnableHystrixClientComponent;
import ro.rasel.spring.microservices.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.swagger.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;
import ro.rasel.spring.microservices.springcommon.EnableSpringCommonsComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
@EnableSpringCommonsComponent
@EnableHystrixClientComponent
@EnableZipkinClientComponent
@EnableAsync
public class PassportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportServiceApplication.class, args);
    }
}
 
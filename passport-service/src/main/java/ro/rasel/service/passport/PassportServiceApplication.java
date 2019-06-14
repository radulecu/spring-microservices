package ro.rasel.service.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.eureka.client.component.EnableEurekaClientComponent;
import ro.rasel.security.client.resource.EnableResourceSecurityClientComponent;
import ro.rasel.ssl.client.EnableSslClientComponent;
import ro.rasel.ssl.server.EnableSslServerComponent;
import ro.rasel.swagger.EnableSwaggerComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
public class PassportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportServiceApplication.class, args);
    }
}
 
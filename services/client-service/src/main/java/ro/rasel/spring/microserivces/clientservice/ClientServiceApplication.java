package ro.rasel.spring.microserivces.clientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.spring.microserivces.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.sso.EnableWebSecurityClientComponent;
import ro.rasel.spring.microserivces.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microserivces.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microserivces.component.swagger.EnableSwaggerComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
@EnableEurekaClientComponent
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
}
 
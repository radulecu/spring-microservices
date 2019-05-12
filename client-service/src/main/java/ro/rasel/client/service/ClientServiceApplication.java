package ro.rasel.client.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.eureka.client.component.EnableEurekaClientComponent;
import ro.rasel.security.client.sso.EnableWebSecurityClientComponent;
import ro.rasel.ssl.keystore.EnableKeystoreComponent;
import ro.rasel.ssl.truststore.EnableTruststoreComponent;
import ro.rasel.swagger.EnableSwaggerComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableSwaggerComponent
@EnableKeystoreComponent
@EnableTruststoreComponent
@EnableWebSecurityClientComponent
@EnableEurekaClientComponent
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
}
 
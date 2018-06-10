package ro.rasel.client.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.EnableWebSecurityClientComponent;
import ro.rasel.service.bookmarks.EnableEurekaClientComponent;
import ro.rasel.ssl.truststore.EnableTruststoreComponent;
import ro.rasel.swagger.EnableSwaggerComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableSwaggerComponent
@EnableTruststoreComponent
@EnableWebSecurityClientComponent
@EnableEurekaClientComponent
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
}
 
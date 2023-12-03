package ro.rasel.client.service.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.eureka.client.component.config.EnableEurekaClientComponent;
import ro.rasel.security.client.sso.EnableWebSecurityClientComponent;
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
public class ClientServiceLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceLightApplication.class, args);
    }
}
 
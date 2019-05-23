package ro.rasel.client.service.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ro.rasel.eureka.client.component.EnableEurekaClientComponent;
import ro.rasel.security.client.sso.EnableWebSecurityClientComponent;
import ro.rasel.ssl.server.EnableSslServerComponent;
import ro.rasel.ssl.client.EnableSslClientComponent;
import ro.rasel.swagger.EnableSwaggerComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
@EnableEurekaClientComponent
public class ClientServiceLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceLightApplication.class, args);
    }
}
 
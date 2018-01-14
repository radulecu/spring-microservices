package ro.rasel.client.service.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.WebSecurityComponent;
import ro.rasel.ssl.configuration.TruststoreComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
public class ClientServiceLightApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(new Class<?>[]{ClientServiceLightApplication.class, WebSecurityComponent.class,
                        TruststoreComponent.class}, args);
    }
}
 
package ro.rasel.client.server.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.WebSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
public class ClientServiceLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{ClientServiceLightApplication.class, WebSecurity.class}, args);
    }
}
 
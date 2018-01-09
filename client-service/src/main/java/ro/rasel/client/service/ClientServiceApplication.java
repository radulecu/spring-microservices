package ro.rasel.client.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.WebSecurityComponent;
import ro.rasel.service.bookmarks.EurekaClientComponent;
import ro.rasel.tls.configuration.SSLComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(new Class<?>[]{ClientServiceApplication.class, EurekaClientComponent.class,
                                WebSecurityComponent.class,
                                SSLComponent.class},
                        args);
    }
}
 
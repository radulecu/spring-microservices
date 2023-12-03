package ro.rasel.client.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.WebSecurityClientComponent;
import ro.rasel.service.bookmarks.EurekaClientComponent;
import ro.rasel.ssl.truststore.TrustStoreComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(new Class<?>[]{ClientServiceApplication.class, EurekaClientComponent.class,
                                WebSecurityClientComponent.class, TrustStoreComponent.class},
                        args);
    }
}
 
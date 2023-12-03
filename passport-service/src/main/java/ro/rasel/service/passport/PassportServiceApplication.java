package ro.rasel.service.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.resource.ResourceSecurityClientComponent;
import ro.rasel.service.bookmarks.EurekaClientComponent;
import ro.rasel.ssl.truststore.TrustStoreComponent;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class PassportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{PassportServiceApplication.class, EurekaClientComponent.class,
                ResourceSecurityClientComponent.class, TrustStoreComponent.class}, args);
    }
}
 
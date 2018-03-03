package ro.rasel.service.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import ro.rasel.security.client.resource.ResourceSecurityClientComponent;
import ro.rasel.service.bookmarks.EurekaClientComponent;
import ro.rasel.ssl.truststore.TrustStoreComponent;
import ro.rasel.swagger.SwaggerConfig;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class PassportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{PassportServiceApplication.class, EurekaClientComponent.class,
                ResourceSecurityClientComponent.class, TrustStoreComponent.class, SwaggerConfig.class}, args);
    }
}
 
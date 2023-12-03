package ro.rasel.service.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.resource.ResourceSecurity;
import ro.rasel.service.bookmarks.EurekaClientConfgurer;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
public class PassportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{PassportServiceApplication.class, EurekaClientConfgurer.class,
                ResourceSecurity.class}, args);
    }
}
 
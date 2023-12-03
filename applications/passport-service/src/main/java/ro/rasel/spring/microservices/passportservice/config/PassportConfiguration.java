package ro.rasel.spring.microservices.passportservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class PassportConfiguration {
    @Bean
    public static Executor executor() {
        return Executors.newFixedThreadPool(10);
    }
}

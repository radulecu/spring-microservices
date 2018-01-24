package ro.rasel.service.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import ro.rasel.ssl.truststore.TrustStoreComponent;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{EurekaServerApplication.class, TrustStoreComponent.class}, args);
    }

}

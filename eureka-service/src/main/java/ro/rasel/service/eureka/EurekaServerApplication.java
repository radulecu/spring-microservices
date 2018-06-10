package ro.rasel.service.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import ro.rasel.security.client.resource.EnableResourceSecurityClientComponent;
import ro.rasel.service.bookmarks.EnableEurekaClientComponent;
import ro.rasel.ssl.truststore.EnableTruststoreComponent;

@SpringBootApplication
@EnableEurekaServer
@EnableTruststoreComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}

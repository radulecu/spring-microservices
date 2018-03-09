package ro.rasel.service.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import ro.rasel.security.client.resource.ResourceSecurityClientComponent;
import ro.rasel.security.client.sso.WebSecurityClientComponent;
import ro.rasel.service.bookmarks.EurekaClientComponent;
import ro.rasel.ssl.truststore.TrustStoreComponent;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{EurekaServerApplication.class, EurekaClientComponent.class, TrustStoreComponent.class,
                ResourceSecurityClientComponent.class, WebSecurityClientComponent.class}, args);
    }

}

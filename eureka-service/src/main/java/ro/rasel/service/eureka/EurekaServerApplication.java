package ro.rasel.service.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import ro.rasel.eureka.client.component.EnableEurekaClientComponent;
import ro.rasel.security.client.resource.EnableResourceSecurityClientComponent;
import ro.rasel.ssl.server.EnableSslServerComponent;
import ro.rasel.ssl.client.EnableSslClientComponent;

@SpringBootApplication
@EnableEurekaServer
@EnableSslServerComponent
@EnableSslClientComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}

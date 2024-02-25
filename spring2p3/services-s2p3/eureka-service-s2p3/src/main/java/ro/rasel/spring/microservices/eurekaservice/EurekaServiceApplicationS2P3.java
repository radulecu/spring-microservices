package ro.rasel.spring.microservices.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import ro.rasel.spring.microservices.component.securityclient.basic.EnableBasicSecurityClientComponent;
import ro.rasel.spring.microservices.component.securityclient.web.EnableWebSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;

@SpringBootApplication
@EnableEurekaServer
@EnableSslServerComponent
@EnableSslClientComponent
@EnableBasicSecurityClientComponent
@EnableWebSecurityClientComponent
//@EnableEurekaClientComponent // needed only if I want also expose eureka service through gateway
public class EurekaServiceApplicationS2P3 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplicationS2P3.class, args);
    }

}

package ro.rasel.spring.microservices.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microservices.component.securityclient.basic.EnableBasicSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;

@SpringBootApplication
@EnableEurekaServer
@EnableSslServerComponent
@EnableSslClientComponent
@EnableResourceSecurityClientComponent
@EnableBasicSecurityClientComponent
@EnableEurekaClientComponent
public class EurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }

}

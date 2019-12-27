package ro.rasel.spring.microserivces.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import ro.rasel.spring.microserivces.component.securityclient.basic.EnableBasicSecurityClientComponent;

@SpringBootApplication
@EnableConfigServer
@EnableBasicSecurityClientComponent
public class ConfigServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}

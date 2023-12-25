package ro.rasel.spring.microservices.ssoauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.swagger2.EnableSwaggerComponent;

@SpringBootApplication
@EnableSslServerComponent
@EnableSwaggerComponent
@EnableResourceServer
public class SsoAuthServiceS2P3Application {
    public static void main(String[] args) {
        SpringApplication.run(SsoAuthServiceS2P3Application.class, args);
    }

}

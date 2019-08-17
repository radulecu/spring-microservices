package ro.rasel.spring.microserivces.ssoauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import ro.rasel.spring.microserivces.component.ssl.server.EnableSslServerComponent;

@SpringBootApplication
@EnableSslServerComponent
@EnableResourceServer
public class SsoAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoAuthServiceApplication.class, args);
    }

}

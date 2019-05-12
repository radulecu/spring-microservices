package ro.rasel.server.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import ro.rasel.ssl.keystore.EnableKeystoreComponent;

@SpringBootApplication
@EnableKeystoreComponent
@EnableResourceServer
public class SsoAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoAuthServerApplication.class, args);
    }

}

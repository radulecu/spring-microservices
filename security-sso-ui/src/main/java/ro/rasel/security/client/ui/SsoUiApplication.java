package ro.rasel.security.client.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.EnableWebSecurityClientComponent;
import ro.rasel.ssl.truststore.EnableTruststoreComponent;

@SpringBootApplication
@EnableZuulProxy
@EnableTruststoreComponent
@EnableWebSecurityClientComponent
public class SsoUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoUiApplication.class, args);
    }

}

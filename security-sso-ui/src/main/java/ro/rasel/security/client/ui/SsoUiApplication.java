package ro.rasel.security.client.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.WebSecurityComponent;
import ro.rasel.ssl.configuration.TruststoreComponent;

@SpringBootApplication
@EnableZuulProxy
public class SsoUiApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(new Class<?>[]{SsoUiApplication.class, WebSecurityComponent.class, TruststoreComponent.class}, args);
    }

}

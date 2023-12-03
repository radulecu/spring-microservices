package ro.rasel.security.client.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.security.client.sso.WebSecurity;

@SpringBootApplication
@EnableZuulProxy
public class SsoUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{SsoUiApplication.class, WebSecurity.class}, args);
    }

}

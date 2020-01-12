package ro.rasel.spring.microservices.ssouitestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import ro.rasel.spring.microservices.component.securityclient.sso.EnableWebSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;

@SpringBootApplication
@EnableZuulProxy
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
public class SsoUiTestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoUiTestServiceApplication.class, args);
    }

}

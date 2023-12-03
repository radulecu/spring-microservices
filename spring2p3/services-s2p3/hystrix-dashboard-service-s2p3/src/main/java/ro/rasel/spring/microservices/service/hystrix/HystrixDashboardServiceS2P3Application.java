package ro.rasel.spring.microservices.service.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ro.rasel.spring.microservices.component.securityclient.web.EnableWebSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;

@Controller
@EnableHystrixDashboard
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
@SpringBootApplication
public class HystrixDashboardServiceS2P3Application {

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    public String home() {
        return "forward:/hystrix";
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardServiceS2P3Application.class, args);
    }

}

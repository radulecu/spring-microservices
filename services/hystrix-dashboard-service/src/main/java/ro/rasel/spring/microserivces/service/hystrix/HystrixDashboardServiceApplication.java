package ro.rasel.spring.microserivces.service.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ro.rasel.spring.microserivces.component.securityclient.sso.EnableWebSecurityClientComponent;
import ro.rasel.spring.microserivces.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microserivces.component.ssl.client.EnableSslClientComponent;

@Controller
@EnableHystrixDashboard
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
@SpringBootApplication
public class HystrixDashboardServiceApplication {

    @GetMapping("/")
    public String home() {
        return "forward:/hystrix";
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardServiceApplication.class, args);
    }

}

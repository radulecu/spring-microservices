package ro.rasel.service.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ro.rasel.security.client.sso.EnableWebSecurityClientComponent;
import ro.rasel.ssl.server.EnableSslServerComponent;
import ro.rasel.ssl.client.EnableSslClientComponent;

@Controller
@EnableHystrixDashboard
@EnableSslServerComponent
@EnableSslClientComponent
@EnableWebSecurityClientComponent
@SpringBootApplication
public class HystrixDashboardApplication {

    @GetMapping("/")
    public String home() {
        return "forward:/hystrix";
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }

}

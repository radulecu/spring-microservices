package ro.rasel.service.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardApplication {

    @GetMapping("/")
    public String home() {
        return "forward:/hystrix/index.html";
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }

}

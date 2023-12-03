package ro.rasel.server.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.server.security.dao.UserDetasDao;

import java.security.Principal;

@SpringBootApplication
@EnableResourceServer
@RestController
public class SsoAuthServerApplication {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @Bean
    UserDetailsService userDetailsService(UserDetasDao userDetasDao) {
        return userDetasDao::findUserByName;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoAuthServerApplication.class, args);
    }

}

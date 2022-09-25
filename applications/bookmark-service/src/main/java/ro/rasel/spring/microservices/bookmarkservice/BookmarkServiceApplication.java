package ro.rasel.spring.microservices.bookmarkservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.rasel.spring.microservices.bookmarkservice.dao.BookmarkRepository;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.swagger.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;
import ro.rasel.spring.microservices.springcommon.EnableSpringCommonsComponent;

import java.util.Arrays;

@SpringBootApplication
@EnableSwaggerComponent
@EnableSslClientComponent
@EnableSslServerComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
@EnableSpringCommonsComponent
@EnableZipkinClientComponent
public class BookmarkServiceApplication {

    @Bean
    public CommandLineRunner init(BookmarkRepository bookmarkRepository) {
        return args -> {
            bookmarkRepository.deleteAll();

            Arrays.asList("pwebb", "jlong")
                    .forEach(n -> bookmarkRepository.save(new Bookmark(n,
                            String.format("http://some-other-host-for-%s.com", n),
                            String.format("A description for %s's link", n),
                            String.format("%sLabel", n))));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BookmarkServiceApplication.class, args);
    }

}

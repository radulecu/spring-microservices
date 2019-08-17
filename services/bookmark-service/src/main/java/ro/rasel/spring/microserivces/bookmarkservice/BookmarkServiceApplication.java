package ro.rasel.spring.microserivces.bookmarkservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.rasel.spring.microserivces.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microserivces.bookmarkservice.dao.BookmarkRepository;
import ro.rasel.spring.microserivces.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microserivces.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microserivces.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microserivces.component.swagger.EnableSwaggerComponent;

import java.util.Arrays;

@SpringBootApplication
@EnableSwaggerComponent
@EnableSslClientComponent
@EnableSslServerComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
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

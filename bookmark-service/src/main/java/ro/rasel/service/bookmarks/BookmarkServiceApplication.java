package ro.rasel.service.bookmarks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.rasel.eureka.client.component.EnableEurekaClientComponent;
import ro.rasel.security.client.resource.EnableResourceSecurityClientComponent;
import ro.rasel.service.bookmarks.dao.BookmarkRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;
import ro.rasel.ssl.client.EnableSslClientComponent;
import ro.rasel.ssl.server.EnableSslServerComponent;
import ro.rasel.swagger.EnableSwaggerComponent;

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

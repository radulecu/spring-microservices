package ro.rasel.spring.microservices.bookmarkservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;

@SpringBootApplication
@EnableSslClientComponent
@EnableSslServerComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
@EnableSpringCommonsComponent
@EnableZipkinClientComponent
@OpenAPIDefinition(info = @Info(title = "Bookmark service", description = "Bookmark REST service", version = "v1",
        license = @License(name = "Apache License, Version 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
@EnableSwaggerComponent
//@SecuritySchemes(
//        @SecurityScheme(name = OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME, type = SecuritySchemeType.OAUTH2,
//                in = SecuritySchemeIn.HEADER,
//                flows = @OAuthFlows(
//                        authorizationCode = @OAuthFlow(authorizationUrl = "${security.server.url}/oauth/authorize",
//                                tokenUrl = "${security.server.url}/oauth/token",
//                                scopes = {@OAuthScope(name = "read"), @OAuthScope(name = "write")}),
//                        password = @OAuthFlow(authorizationUrl = "${security.server.url}/oauth/authorize",
//                                tokenUrl = "${security.server.url}/oauth/token",
//                                scopes = {@OAuthScope(name = "read"), @OAuthScope(name = "write")}))))
public class BookmarkServiceApplication {

    @Bean
    @Transactional
    public CommandLineRunner init(BookmarkRepository bookmarkRepository) {
        return args -> Arrays.asList("pwebb", "jlong")
                .forEach(user -> addBookmarkIfNotExist(bookmarkRepository, user));
    }

    private static void addBookmarkIfNotExist(BookmarkRepository bookmarkRepository, String user) {
        String label = String.format("%sLabel", user);
        List<Bookmark> byUserIdAndLabel = bookmarkRepository.findByUserIdAndLabel(user, label);
        if (byUserIdAndLabel.isEmpty()) {
            bookmarkRepository.save(new Bookmark(user,
                    String.format("http://some-other-host-for-%s.com", user),
                    String.format("A description for %s's link", user),
                    label));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BookmarkServiceApplication.class, args);
    }

}

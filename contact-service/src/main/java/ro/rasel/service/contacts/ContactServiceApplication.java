package ro.rasel.service.contacts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.rasel.security.client.resource.EnableResourceSecurityClientComponent;
import ro.rasel.service.bookmarks.EnableEurekaClientComponent;
import ro.rasel.service.contacts.dao.ContactRepository;
import ro.rasel.service.contacts.domain.Contact;
import ro.rasel.ssl.truststore.EnableTruststoreComponent;
import ro.rasel.swagger.EnableSwaggerComponent;

import java.util.Arrays;

@SpringBootApplication
@EnableSwaggerComponent
@EnableTruststoreComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
public class ContactServiceApplication {

    @Bean
    public CommandLineRunner init(ContactRepository contactRepository) {
        return args -> {
            contactRepository.deleteAll();
            Arrays.asList("pwebb,jlong".split(",")).forEach(userId -> Arrays.stream(
                    "Stéphane,Maldini;Dave,Syer;Juergen,Hoeller;Rob,Winch;Mark,Fisher;Arjen,Poutsma"
                            .split(";")).map(name -> name.split(","))
                    .map(firstLastName -> new Contact(userId, firstLastName[0],
                            firstLastName[1],
                            firstLastName[0].toLowerCase() + "@email.com", "friend"))
                    .forEach(contactRepository::save));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ContactServiceApplication.class, args);
    }

}

package ro.rasel.spring.microserivces.contactservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.rasel.spring.microserivces.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microserivces.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microserivces.contactservice.dao.ContactRepository;
import ro.rasel.spring.microserivces.contactservice.domain.Contact;
import ro.rasel.spring.microserivces.springcommons.EnableSpringCommonsComponent;
import ro.rasel.spring.microserivces.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microserivces.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microserivces.component.swagger.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;

import java.util.Arrays;

@SpringBootApplication
@EnableSwaggerComponent
@EnableSslServerComponent
@EnableSslClientComponent
@EnableResourceSecurityClientComponent
@EnableEurekaClientComponent
@EnableSpringCommonsComponent
@EnableZipkinClientComponent
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

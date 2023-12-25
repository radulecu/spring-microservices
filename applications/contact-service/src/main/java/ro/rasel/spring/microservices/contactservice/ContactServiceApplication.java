package ro.rasel.spring.microservices.contactservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import ro.rasel.spring.microservices.component.eurekaclient.EnableEurekaClientComponent;
import ro.rasel.spring.microservices.component.securityclient.resource.EnableResourceSecurityClientComponent;
import ro.rasel.spring.microservices.component.ssl.client.EnableSslClientComponent;
import ro.rasel.spring.microservices.component.ssl.server.EnableSslServerComponent;
import ro.rasel.spring.microservices.component.swagger2.EnableSwaggerComponent;
import ro.rasel.spring.microservices.component.zipkin.EnableZipkinClientComponent;
import ro.rasel.spring.microservices.contactservice.dao.ContactRepository;
import ro.rasel.spring.microservices.contactservice.domain.Address;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.domain.PhoneNumber;
import ro.rasel.spring.microservices.springcommon.EnableSpringCommonsComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    @Transactional
    public CommandLineRunner init(ContactRepository contactRepository) {
        return args -> Arrays.asList("pwebb,jlong".split(","))
                .forEach(userId -> Arrays.stream(
                                "Stéphane,Maldini;Dave,Syer;Juergen,Hoeller;Rob,Winch;Mark,Fisher;Arjen,Poutsma"
                                        .split(";")).map(name -> name.split(","))
                        .forEach(firstLastName -> {
                            String email = firstLastName[0].toLowerCase() + "@email.com";
                            List<Contact> byUserIdAndEmail = contactRepository.findByUserIdAndEmail(userId, email);
                            if (byUserIdAndEmail.isEmpty()) {
                                final Random random = new Random(123251);
                                Contact contact = new Contact(userId, firstLastName[0],
                                        firstLastName[1],
                                        email, "friend",
                                        Arrays.asList(new PhoneNumber("+40723123456",
                                                        "phone number description " + firstLastName[0] + " " + firstLastName[1]),
                                                new PhoneNumber("+40723123457", null)),
                                        Arrays.asList(
                                                new Address(firstLastName[0] + " country", firstLastName[0] + " address",
                                                        firstLastName[0] + " street", random.nextInt(200), 3, 123),
                                                new Address(firstLastName[0] + " country2", firstLastName[0] + " address2",
                                                        firstLastName[0] + " street2", random.nextInt(200), null, null)));
                                contactRepository.save(contact);
                            }
                        }));
    }

    public static void main(String[] args) {
        SpringApplication.run(ContactServiceApplication.class, args);
    }

}

package ro.rasel.service.passport.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.service.passport.domain.Contact;

import java.util.Collection;

@FeignClient("contact-service")
interface ContactClient {

    @GetMapping(value = "/contacts/{userId}")
    Collection<Contact> getContacts(@PathVariable("userId") String userId);

}

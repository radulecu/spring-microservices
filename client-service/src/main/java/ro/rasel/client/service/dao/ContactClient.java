package ro.rasel.client.service.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.rasel.client.service.domain.Contact;

import java.util.Collection;

@FeignClient("contact-service")
interface ContactClient {

    @RequestMapping(method = RequestMethod.GET, value = "/contacts/{userId}")
    Collection<Contact> getContacts(@PathVariable("userId") String userId);

}
package ro.rasel.spring.microserivces.passportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.spring.microserivces.api.contact.ContactApi;

@FeignClient("contact-service")
interface ContactClient extends ContactApi {

}

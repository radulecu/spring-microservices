package ro.rasel.spring.microservices.clientservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.spring.microservices.api.contact.ContactApi;

@FeignClient("contact-service")
interface ContactClient extends ContactApi {

}

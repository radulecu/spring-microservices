package ro.rasel.spring.microservices.passportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.spring.microservices.api.contact.ContactApi;

@FeignClient(name="contact-service", url="${feign.client.contact-service.url:}")
interface ContactClient extends ContactApi {

}

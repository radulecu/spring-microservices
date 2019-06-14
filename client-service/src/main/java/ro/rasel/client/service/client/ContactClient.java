package ro.rasel.client.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.contact.api.ContactApi;

@FeignClient("contact-service")
interface ContactClient extends ContactApi {

}

package ro.rasel.spring.microserivces.clientservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microserivces.clientservice.domain.Passport;

@FeignClient("passport-service")
public interface PassportClient {

    @GetMapping(value = "/users/{userId}/passport")
    Passport getPassport(@PathVariable("userId") String userId);

}

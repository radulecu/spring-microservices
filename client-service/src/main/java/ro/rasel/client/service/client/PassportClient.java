package ro.rasel.client.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.client.service.domain.Passport;

@FeignClient("passport-service")
public interface PassportClient {

    @GetMapping(value = "/passport/{userId}")
    Passport getPassport(@PathVariable("userId") String userId);

}

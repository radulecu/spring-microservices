package ro.rasel.client.service.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.rasel.client.service.domain.Passport;

@FeignClient("passport-service")
public interface PassportClient {

    @RequestMapping(method = RequestMethod.GET, value = "/passport/{userId}")
    Passport getPassport(@PathVariable("userId") String userId);

}

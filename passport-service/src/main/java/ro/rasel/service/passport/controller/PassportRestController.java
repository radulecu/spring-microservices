package ro.rasel.service.passport.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.passport.client.IntegrationClient;
import ro.rasel.service.passport.domain.Passport;

@RestController
@RequestMapping("/passport/{userId}")
class PassportRestController {

    private final IntegrationClient integrationClient;

    public PassportRestController(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    Passport passport(@PathVariable String userId) {
        return new Passport(userId, this.integrationClient.getContacts(userId),
                this.integrationClient.getBookmarks(userId));
    }

}

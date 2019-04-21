package ro.rasel.service.passport.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.passport.client.IntegrationClient;
import ro.rasel.service.passport.domain.Passport;

@RestController
@RequestMapping("/users/{userId}/passport")
class PassportRestController {

    private final IntegrationClient integrationClient;

    public PassportRestController(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @GetMapping
    Passport passport(@PathVariable("userId") String userId) {
        return new Passport(userId, this.integrationClient.getContacts(userId),
                this.integrationClient.getBookmarks(userId));
    }

}

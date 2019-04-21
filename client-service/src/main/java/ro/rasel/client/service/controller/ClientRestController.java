package ro.rasel.client.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.client.service.client.IntegrationClient;
import ro.rasel.client.service.domain.Contact;
import ro.rasel.client.service.domain.Passport;

import java.util.Collection;

@RestController
class ClientRestController {

    private final IntegrationClient integrationClient;

    @Autowired
    ClientRestController(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @GetMapping("/users/{userId}/passport")
    Passport passport(@PathVariable String userId) {
        return integrationClient.getPassport(userId);
    }

    @GetMapping("/users/{userId}/bookmarks")
    String bookmarks(@PathVariable String userId) {
        return integrationClient.getBookmarks(userId);
    }

    @GetMapping("/users/{userId}/contacts")
    Collection<Contact> contacts(@PathVariable String userId) {
        return integrationClient.getContacts(userId);
    }
}

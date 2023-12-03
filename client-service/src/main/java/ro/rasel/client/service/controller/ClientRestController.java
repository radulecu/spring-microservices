package ro.rasel.client.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.client.service.dao.IntegrationClient;
import ro.rasel.client.service.domain.Bookmark;
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

    @RequestMapping("/passport/{userId}")
    Passport passport(@PathVariable String userId) {
        return integrationClient.getPassport(userId);
    }

    @RequestMapping("/bookmarks/{userId}")
    Collection<Bookmark> bookmarks(@PathVariable String userId) {
        return integrationClient.getBookmarks(userId);
    }

    @RequestMapping("/contacts/{userId}")
    Collection<Contact> contacts(@PathVariable String userId) {
        return integrationClient.getContacts(userId);
    }

    @RequestMapping("/hi")
    String hi(@PathVariable String userId) {
        return "hi";
    }

}

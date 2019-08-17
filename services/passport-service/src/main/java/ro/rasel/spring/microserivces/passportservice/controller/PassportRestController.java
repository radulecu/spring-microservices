package ro.rasel.spring.microserivces.passportservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microserivces.api.bookmark.domain.Bookmark;
import ro.rasel.spring.microserivces.api.contact.domain.Contact;
import ro.rasel.spring.microserivces.passportservice.domain.Passport;
import ro.rasel.spring.microserivces.passportservice.client.IntegrationClient;

import java.util.Collection;

@RestController
public class PassportRestController implements PassportApi {

    private final IntegrationClient integrationClient;

    public PassportRestController(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @Override
    public ResponseEntity<Passport> passport(@PathVariable String userId) {
        final Collection<Contact> contacts = this.integrationClient.getContacts(userId);
        final Collection<Bookmark> bookmarks = this.integrationClient.getBookmarks(userId);
        return bookmarks.isEmpty() && contacts.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(new Passport(userId, contacts, bookmarks));
    }
}

package ro.rasel.spring.microservices.passportservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.api.bookmark.data.Bookmark;
import ro.rasel.spring.microservices.api.contact.data.Contact;
import ro.rasel.spring.microservices.passportservice.client.IntegrationClient;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

import java.util.Collection;

@Service
public class PassportServiceImpl implements PassportService {

    private final IntegrationClient integrationClient;

    public PassportServiceImpl(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @Override
    public Passport getPassport(@PathVariable String userId) {
        final Collection<Contact> contacts = this.integrationClient.getContacts(userId);
        final Collection<Bookmark> bookmarks = this.integrationClient.getBookmarks(userId);
        return bookmarks.isEmpty() && contacts.isEmpty() ? null : new Passport(userId, bookmarks, contacts);
    }
}

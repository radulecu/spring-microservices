package ro.rasel.service.passport.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import ro.rasel.service.passport.domain.Bookmark;
import ro.rasel.service.passport.domain.Contact;

import java.util.Collection;
import java.util.Collections;

@Component
public class IntegrationClient {

    private final ContactClient contactClient;

    private final BookmarkClient bookmarkClient;

    public IntegrationClient(ContactClient contactClient, BookmarkClient bookmarkClient) {
        this.contactClient = contactClient;
        this.bookmarkClient = bookmarkClient;
    }

    @SuppressWarnings("unused")
    public Collection<Bookmark> getBookmarksFallback(String userId) {
        System.out.println("getBookmarksFallback");
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getBookmarksFallback")
    public Collection<Bookmark> getBookmarks(String userId) {
        return this.bookmarkClient.getBookmarks(userId);
    }

    @SuppressWarnings("unused")
    public Collection<Contact> getContactsFallback(String userId) {
        System.out.println("getContactsFallback");
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getContactsFallback")
    public Collection<Contact> getContacts(String userId) {
        return this.contactClient.getContacts(userId);
    }
}
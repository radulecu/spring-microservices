package ro.rasel.spring.microservices.passportservice.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.rasel.spring.microservices.api.bookmark.data.Bookmark;
import ro.rasel.spring.microservices.api.contact.data.Contact;

import java.util.Collection;
import java.util.Collections;

@Component
public class IntegrationClient {
    private static final Logger LOG = LoggerFactory.getLogger(IntegrationClient.class);

    private final ContactClient contactClient;

    private final BookmarkClient bookmarkClient;

    public IntegrationClient(ContactClient contactClient, BookmarkClient bookmarkClient) {
        this.contactClient = contactClient;
        this.bookmarkClient = bookmarkClient;
    }

    @SuppressWarnings("unused")
    public Collection<Bookmark> getBookmarksFallback(String userId, Throwable t) {
        LOG.error("getBookmarksFallback", t);
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getBookmarksFallback")
    public Collection<Bookmark> getBookmarks(String userId) {
        LOG.info("getting bookmarks form bookmark service for userId={}", userId);
        return this.bookmarkClient.getBookmarks(userId).getBody();
    }

    @SuppressWarnings("unused")
    public Collection<Contact> getContactsFallback(String userId, Throwable t) {
        LOG.error("getContactsFallback", t);
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getContactsFallback")
    public Collection<Contact> getContacts(String userId) {
        LOG.info("getting contacts form bookmark service for userId={}", userId);
        return this.contactClient.getContacts(userId).getBody();
    }
}
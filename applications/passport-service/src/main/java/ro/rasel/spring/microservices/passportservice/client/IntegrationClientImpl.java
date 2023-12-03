package ro.rasel.spring.microservices.passportservice.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;
import java.util.Collections;

@Component
public class IntegrationClientImpl implements IntegrationClient {
    private static final Logger LOG = LoggerFactory.getLogger(IntegrationClientImpl.class);

    private final ContactClient contactClient;

    private final BookmarkClient bookmarkClient;

    public IntegrationClientImpl(ContactClient contactClient, BookmarkClient bookmarkClient) {
        this.contactClient = contactClient;
        this.bookmarkClient = bookmarkClient;
    }

    @SuppressWarnings("unused")
    public Collection<BookmarkResponse> getBookmarksFallback(String userId, Throwable t) {
        LOG.error("getBookmarksFallback", t);
        return Collections.emptyList();
    }

    @Override
    @Bulkhead(name = BookmarkClient.NAME, fallbackMethod = "getBookmarksFallback")
    @CircuitBreaker(name = BookmarkClient.NAME, fallbackMethod = "getBookmarksFallback")
    @Retry(name = BookmarkClient.NAME, fallbackMethod = "getBookmarksFallback")
    public Collection<BookmarkResponse> getBookmarks(String userId) {
        LOG.info("getting bookmarks form bookmark service for userId={}", userId);
        return this.bookmarkClient.getBookmarks(userId).getBody();
    }

    @SuppressWarnings("unused")
    public Collection<ContactResponse> getContactsFallback(String userId, Throwable t) {
        LOG.error("getContactsFallback", t);
        return Collections.emptyList();
    }


    @Override
    @Bulkhead(name = ContactClient.NAME, fallbackMethod = "getContactsFallback")
    @CircuitBreaker(name = ContactClient.NAME, fallbackMethod = "getContactsFallback")
    @Retry(name = ContactClient.NAME, fallbackMethod = "getContactsFallback")
    public Collection<ContactResponse> getContacts(String userId) {
        LOG.info("getting contacts form bookmark service for userId={}", userId);
        return this.contactClient.getContacts(userId).getBody();
    }
}
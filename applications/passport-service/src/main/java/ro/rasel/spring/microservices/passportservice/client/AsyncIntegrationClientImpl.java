package ro.rasel.spring.microservices.passportservice.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.ContactApi;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Component
public class AsyncIntegrationClientImpl implements AsyncIntegrationClient {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncIntegrationClientImpl.class);

    private final ContactClient contactClient;

    private final BookmarkClient bookmarkClient;

    public AsyncIntegrationClientImpl(ContactClient contactClient,
                                      BookmarkClient bookmarkClient) {
        this.contactClient = contactClient;
        this.bookmarkClient = bookmarkClient;
    }

    @SuppressWarnings("unused")
    public Future<Collection<BookmarkResponse>> getBookmarksFallback(String userId, Throwable t) {
        LOG.error("getBookmarksFallback", t);
        return new AsyncResult<>(Collections.emptyList());
    }

    @Override
    @Async
    @Bulkhead(name = BookmarkClient.NAME, fallbackMethod = "getBookmarksFallback")
    @CircuitBreaker(name = BookmarkClient.NAME, fallbackMethod = "getBookmarksFallback")
    @Retry(name = BookmarkClient.NAME, fallbackMethod = "getBookmarksFallback")
    public Future<Collection<BookmarkResponse>> getBookmarks(String userId) {
        LOG.info("getting bookmarks form bookmark service for userId={}", userId);
        return AsyncResult.forValue(this.bookmarkClient.getBookmarks(userId).getBody());
    }

    @SuppressWarnings("unused")
    public Future<Collection<ContactResponse>> getContactsFallback(String userId, Throwable t) {
        LOG.error("getContactsFallback", t);
        return new AsyncResult<>(Collections.emptyList());
    }

    @Override
    @Async
    @Bulkhead(name = ContactClient.NAME, fallbackMethod = "getContactsFallback")
    @CircuitBreaker(name = ContactClient.NAME, fallbackMethod = "getContactsFallback")
    @Retry(name = ContactClient.NAME, fallbackMethod = "getContactsFallback")
    public Future<Collection<ContactResponse>> getContacts(String userId) {
        LOG.info("getting contacts form bookmark service for userId={}", userId);
        return AsyncResult.forValue(this.contactClient.getContacts(userId).getBody());
    }
}
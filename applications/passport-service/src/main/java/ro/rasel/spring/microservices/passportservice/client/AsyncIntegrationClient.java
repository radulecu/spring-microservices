package ro.rasel.spring.microservices.passportservice.client;

import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncIntegrationClient {
    Future<Collection<BookmarkResponse>> getBookmarks(String userId);

    Future<Collection<ContactResponse>> getContacts(String userId);
}

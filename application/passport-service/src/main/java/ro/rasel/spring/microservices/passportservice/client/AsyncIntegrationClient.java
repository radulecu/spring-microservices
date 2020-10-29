package ro.rasel.spring.microservices.passportservice.client;

import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import rx.Observable;

import java.util.Collection;

public interface AsyncIntegrationClient {
    Observable<Collection<BookmarkResponse>> getBookmarks(String userId);

    Observable<Collection<ContactResponse>> getContacts(String userId);
}

package ro.rasel.spring.microservices.passportservice.client;

import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;

public interface IntegrationClient {
    Collection<BookmarkResponse> getBookmarks(String userId);

    Collection<ContactResponse> getContacts(String userId);
}

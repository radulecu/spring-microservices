package ro.rasel.spring.microservices.passportservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import ro.rasel.spring.microservices.passportservice.client.AsyncIntegrationClient;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AsyncPassportServiceImpl implements AsyncPassportService {

    private final AsyncIntegrationClient integrationClient;

    public AsyncPassportServiceImpl(AsyncIntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @Override
    public Future<Passport> getPassport(@PathVariable String userId) throws ExecutionException, InterruptedException {
        Future<Collection<BookmarkResponse>> bookmarks = this.integrationClient.getBookmarks(userId);
        Future<Collection<ContactResponse>> contacts = this.integrationClient.getContacts(userId);
        Collection<BookmarkResponse> bookmarkResponses = bookmarks.get();
        Collection<ContactResponse> contactResponses = contacts.get();
        return (bookmarkResponses.isEmpty() && contactResponses.isEmpty() ? AsyncResult.forValue(null) :
                AsyncResult.forValue(new Passport(userId, bookmarkResponses, contactResponses)));
    }
}

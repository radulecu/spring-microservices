package ro.rasel.spring.microservices.passportservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.passportservice.client.AsyncIntegrationClient;
import ro.rasel.spring.microservices.passportservice.domain.Passport;

import java.util.concurrent.CompletableFuture;

import static ro.rasel.spring.microservices.commons.utils.future.RxFutureConverterUtils.toCompletableFuture;

@Service
public class AsyncPassportServiceImpl implements AsyncPassportService {

    private final AsyncIntegrationClient integrationClient;

    public AsyncPassportServiceImpl(AsyncIntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @Override
    @Async
    public CompletableFuture<Passport> getPassport(@PathVariable String userId) {
        return toCompletableFuture(this.integrationClient.getBookmarks(userId).toSingle())
                .thenCombineAsync(toCompletableFuture(this.integrationClient.getContacts(userId).toSingle()),
                        (bookmarks, contacts) ->
                                bookmarks.isEmpty() && contacts.isEmpty() ? null :
                                        new Passport(userId, bookmarks, contacts));
    }
}

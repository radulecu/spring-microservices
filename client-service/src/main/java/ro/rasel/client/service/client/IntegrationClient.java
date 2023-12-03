package ro.rasel.client.service.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import ro.rasel.client.service.domain.Contact;
import ro.rasel.client.service.domain.Passport;

import java.util.Collection;
import java.util.Collections;

@Component
public class IntegrationClient {

    private final ContactClient contactClient;

    private final BookmarkClient bookmarkClient;

    private final PassportClient passportClient;

    public IntegrationClient(ContactClient contactClient, BookmarkClient bookmarkClient,
                             PassportClient passportClient) {
        this.contactClient = contactClient;
        this.bookmarkClient = bookmarkClient;
        this.passportClient = passportClient;
    }

    @SuppressWarnings("unused")
    public String getBookmarksFallback(String userId) {
        System.out.println("getBookmarksFallback");
        return Collections.emptyList().toString();
    }

    // you can return directly the rest as string when you do not need it in the object format (e.g. you just forward it to other service) ...
    @HystrixCommand(fallbackMethod = "getBookmarksFallback")
    public String getBookmarks(String userId) {
        return this.bookmarkClient.getBookmarks(userId);
    }

    @SuppressWarnings("unused")
    public Collection<Contact> getContactsFallback(String userId) {
        System.out.println("getContactsFallback");
        return Collections.emptyList();
    }

    // ... or you can use a collection of objects ...
    @HystrixCommand(fallbackMethod = "getContactsFallback")
    public Collection<Contact> getContacts(String userId) {
        return this.contactClient.getContacts(userId);
    }

    // ... or just a single object
    @SuppressWarnings("unused")
    public Passport getPassportFallback(String userId) {
        System.out.println("getPassportFallback");
        return null;
    }

    @HystrixCommand(fallbackMethod = "getPassportFallback")
    public Passport getPassport(String userId) {
        return this.passportClient.getPassport(userId);
    }

}

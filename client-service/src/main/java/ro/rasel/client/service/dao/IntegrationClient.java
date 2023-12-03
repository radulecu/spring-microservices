package ro.rasel.client.service.dao;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import ro.rasel.client.service.domain.Bookmark;
import ro.rasel.client.service.domain.Contact;
import ro.rasel.client.service.domain.Passport;

import java.util.Arrays;
import java.util.Collection;

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

    public Collection<Bookmark> getBookmarksFallback(String userId) {
        System.out.println("getBookmarksFallback");
        return Arrays.asList();
    }

    @HystrixCommand(fallbackMethod = "getBookmarksFallback")
    public Collection<Bookmark> getBookmarks(String userId) {
        return this.bookmarkClient.getBookmarks(userId);
    }

    public Collection<Contact> getContactsFallback(String userId) {
        System.out.println("getContactsFallback");
        return Arrays.asList();
    }

    @HystrixCommand(fallbackMethod = "getContactsFallback")
    public Collection<Contact> getContacts(String userId) {
        return this.contactClient.getContacts(userId);
    }

    public Passport getPassportFallback(String userId) {
        System.out.println("getPassportFallback");
        return null;
    }

    @HystrixCommand(fallbackMethod = "getPassportFallback")
    public Passport getPassport(String userId) {
        try{
            return this.passportClient.getPassport(userId);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

}

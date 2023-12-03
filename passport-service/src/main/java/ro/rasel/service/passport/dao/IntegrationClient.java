package ro.rasel.service.passport.dao;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import ro.rasel.service.passport.domain.Bookmark;
import ro.rasel.service.passport.domain.Contact;

import java.util.Arrays;
import java.util.Collection;

@Component
public class IntegrationClient {

    private final ContactClient contactClient;

    private final BookmarkClient bookmarkClient;

    public IntegrationClient(ContactClient contactClient, BookmarkClient bookmarkClient) {
        this.contactClient = contactClient;
        this.bookmarkClient = bookmarkClient;
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
        try {
            return this.contactClient.getContacts(userId);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}

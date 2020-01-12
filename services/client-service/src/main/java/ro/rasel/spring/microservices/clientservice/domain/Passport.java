package ro.rasel.spring.microservices.clientservice.domain;

import ro.rasel.spring.microservices.api.bookmark.domain.Bookmark;
import ro.rasel.spring.microservices.api.contact.domain.Contact;

import java.util.Collection;

public class Passport {

    private String userId;

    private Collection<Bookmark> bookmarks;

    private Collection<Contact> contacts;

    public String getUserId() {
        return userId;
    }

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public Collection<Bookmark> getBookmarks() {
        return bookmarks;
    }

    @Override
    public String toString() {
        return "Passport{" + "userId='" + userId + '\'' + ", bookmarks=" + bookmarks
                + ", contacts=" + contacts + '}';
    }

}

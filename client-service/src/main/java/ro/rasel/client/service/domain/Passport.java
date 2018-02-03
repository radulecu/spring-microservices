package ro.rasel.client.service.domain;

import ro.rasel.domain.IPassport;

import java.util.Collection;

public class Passport implements IPassport {

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

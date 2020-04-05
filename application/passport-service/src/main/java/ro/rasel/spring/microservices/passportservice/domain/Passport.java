package ro.rasel.spring.microservices.passportservice.domain;

import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

public class Passport {
    private final String userId;
    private final Collection<BookmarkResponse> bookmarks;
    private final Collection<ContactResponse> contacts;

    public Passport(
            String userId, Collection<BookmarkResponse> bookmarks, Collection<ContactResponse> contacts) {
        this.userId = userId;
        this.bookmarks = bookmarks;
        this.contacts = contacts;
    }

    public String getUserId() {
        return userId;
    }

    public Collection<ContactResponse> getContacts() {
        return contacts;
    }

    public Collection<BookmarkResponse> getBookmarks() {
        return bookmarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return Objects.equals(userId, passport.userId) &&
                Objects.equals(bookmarks, passport.bookmarks) &&
                Objects.equals(contacts, passport.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookmarks, contacts);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Passport.class.getSimpleName() + "[", "]")
                .add("userId='" + userId + "'")
                .add("bookmarks=" + bookmarks)
                .add("contacts=" + contacts)
                .toString();
    }

}

package ro.rasel.service.passport.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.bookmark.domain.Bookmark;
import ro.rasel.contact.domain.Contact;

import java.util.Collection;
import java.util.StringJoiner;

@ApiModel(description = "User Passport")
public class Passport {

    private final String userId;

    private final Collection<Bookmark> bookmarks;

    private final Collection<Contact> contacts;

    public Passport(
            String userId, Collection<Contact> contacts,
            Collection<Bookmark> bookmarks) {
        this.userId = userId;
        this.contacts = contacts;
        this.bookmarks = bookmarks;
    }

    @ApiModelProperty(value = "Passport user id")
    public String getUserId() {
        return userId;
    }

    @ApiModelProperty(value = "Passport contacts")
    public Collection<Contact> getContacts() {
        return contacts;
    }

    @ApiModelProperty(value = "Passport passports")
    public Collection<Bookmark> getBookmarks() {
        return bookmarks;
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

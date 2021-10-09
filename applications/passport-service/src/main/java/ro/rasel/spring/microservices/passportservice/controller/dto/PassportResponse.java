package ro.rasel.spring.microservices.passportservice.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;
import java.util.StringJoiner;

@ApiModel(value = "Passport", description = "User Passport")
public class PassportResponse {
    private final String userId;
    private final Collection<BookmarkResponse> bookmarks;
    private final Collection<ContactResponse> contacts;

    public PassportResponse(
            String userId, Collection<BookmarkResponse> bookmarks,
            Collection<ContactResponse> contacts) {
        this.userId = userId;
        this.bookmarks = bookmarks;
        this.contacts = contacts;
    }

    @ApiModelProperty(value = "Passport user id")
    public String getUserId() {
        return userId;
    }

    @ApiModelProperty(value = "Passport bookmark")
    public Collection<BookmarkResponse> getBookmarks() {
        return bookmarks;
    }

    @ApiModelProperty(value = "Passport contacts")
    public Collection<ContactResponse> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PassportResponse.class.getSimpleName() + "[", "]")
                .add("userId='" + userId + "'")
                .add("bookmarks=" + bookmarks)
                .add("contacts=" + contacts)
                .toString();
    }
}

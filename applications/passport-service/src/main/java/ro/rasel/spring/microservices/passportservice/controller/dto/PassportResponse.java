package ro.rasel.spring.microservices.passportservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Schema(name = "Passport", description = "User Passport")
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

    @Schema(description = "Passport user id")
    public String getUserId() {
        return userId;
    }

    @Schema(description = "Passport bookmark")
    public Collection<BookmarkResponse> getBookmarks() {
        return bookmarks;
    }

    @Schema(description = "Passport contacts")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PassportResponse that = (PassportResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookmarks, that.bookmarks) &&
                Objects.equals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookmarks, contacts);
    }
}

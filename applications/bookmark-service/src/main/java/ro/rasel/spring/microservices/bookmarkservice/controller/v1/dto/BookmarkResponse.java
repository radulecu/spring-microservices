package ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Schema(title = "BookmarkResponse", description = "User bookmark response")
public class BookmarkResponse extends BookmarkDto {
    @ConstructorProperties({"id", "userId", "href", "description", "label"})
    public BookmarkResponse(long id, String userId, String href, String description, String label) {
        this(new Bookmark(id, userId, href, description, label));
    }

    public BookmarkResponse(Bookmark bookmark) {
        super(bookmark);
    }

    @Schema(title = "Bookmark id", example = "2")
    public Long getId() {
        return getBookmark().getId();
    }

    @Schema(title = "Used id", example = "jlong")
    @NotBlank
    public String getUserId() {
        return getBookmark().getUserId();
    }
}

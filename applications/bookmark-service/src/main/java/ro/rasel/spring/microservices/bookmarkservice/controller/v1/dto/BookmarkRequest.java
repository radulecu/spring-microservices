package ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import java.beans.ConstructorProperties;

@Schema(title = "BookmarkRequest", description = "User bookmark")
public class BookmarkRequest extends BookmarkDto {
    @ConstructorProperties({"href", "description", "label"})
    public BookmarkRequest(String href, String description, String label) {
        super(href, description, label);
    }

    public BookmarkRequest(Bookmark bookmark) {
        super(bookmark);
    }
}

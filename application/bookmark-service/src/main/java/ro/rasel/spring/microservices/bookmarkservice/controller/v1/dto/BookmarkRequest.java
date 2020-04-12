package ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import java.beans.ConstructorProperties;

@ApiModel(value = "BookmarkRequest", description = "User Bookmark")
public class BookmarkRequest extends BookmarkDto {
    @ConstructorProperties({"href", "description", "label"})
    public BookmarkRequest(String href, String description, String label) {
        super(href, description, label);
    }

    public BookmarkRequest(Bookmark bookmark) {
        super(bookmark);
    }
}

package ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@ApiModel(value = "BookmarkResponse", description = "User Bookmark Response")
public class BookmarkResponse extends BookmarkDto {
    @ConstructorProperties({"id", "userId", "href", "description", "label"})
    public BookmarkResponse(long id, String userId, String href, String description, String label) {
        this(new Bookmark(id, userId, href, description, label));
    }

    public BookmarkResponse(Bookmark bookmark) {
        super(bookmark);
    }

    @ApiModelProperty(value = "Bookmark id", example = "2")
    public Long getId() {
        return getBookmark().getId();
    }

    @ApiModelProperty(value = "Used id", example = "jlong")
    @NotBlank
    public String getUserId() {
        return getBookmark().getUserId();
    }
}

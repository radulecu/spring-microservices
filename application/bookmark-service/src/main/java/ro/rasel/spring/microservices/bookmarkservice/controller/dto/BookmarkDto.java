package ro.rasel.spring.microservices.bookmarkservice.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@ApiModel(value = "Bookmark", description = "User Bookmark")
public class BookmarkDto extends BookmarkDetailsDto {
    @ConstructorProperties({"id", "userId", "href", "description", "label"})
    public BookmarkDto(long id, String userId, String href, String description, String label) {
        this(new Bookmark(id, userId, href, description, label));
    }

    public BookmarkDto(Bookmark bookmark) {
        super(bookmark);
    }

    @ApiModelProperty(value = "Bookmark id")
    public Long getId() {
        return getBookmark().getId();
    }

    @ApiModelProperty(value = "Used id")
    @NotBlank
    public String getUserId() {
        return getBookmark().getUserId();
    }
}

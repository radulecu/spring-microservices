package ro.rasel.spring.microservices.bookmarkservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel(value = "BookmarkDetails", description = "User Bookmark")
public class BookmarkDetailsDto {
    private final Bookmark bookmark;

    @ConstructorProperties({"href", "description", "label"})
    public BookmarkDetailsDto(String href, String description, String label) {
        this(new Bookmark(href, description, label));
    }

    public BookmarkDetailsDto(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    @NotNull
    @ApiModelProperty(value = "Bookmark href", required = true)
    public String getHref() {
        return bookmark.getHref();
    }

    @NotNull
    @ApiModelProperty(value = "Bookmark description", required = true)
    public String getDescription() {
        return bookmark.getDescription();
    }

    @NotNull
    @ApiModelProperty(value = "Bookmark label", required = true)
    public String getLabel() {
        return bookmark.getLabel();
    }

    @ApiIgnore
    @JsonIgnore
    public Bookmark getBookmark() {
        return bookmark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookmarkDetailsDto that = (BookmarkDetailsDto) o;
        return Objects.equals(bookmark, that.bookmark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmark);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("bookmark=" + bookmark)
                .toString();
    }
}

package ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

public class BookmarkDto {
    private final Bookmark bookmark;

    public BookmarkDto(String href, String description, String label) {
        this(new Bookmark(href, description, label));
    }

    public BookmarkDto(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    @NotNull
    @ApiModelProperty(value = "Bookmark href", required = true, example = "http://some-other-host-for-jlong.com")
    public String getHref() {
        return bookmark.getHref();
    }

    @NotNull
    @ApiModelProperty(value = "Bookmark description", required = true, example = "A description for jlong's link")
    public String getDescription() {
        return bookmark.getDescription();
    }

    @NotNull
    @ApiModelProperty(value = "Bookmark label", required = true, example = "jlongLabel")
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
        BookmarkDto that = (BookmarkDto) o;
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

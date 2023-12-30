package ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public class BookmarkDto {
    private final Bookmark bookmark;

    public BookmarkDto(String href, String description, String label) {
        this(new Bookmark(href, description, label));
    }

    public BookmarkDto(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    @NotNull
    @Schema(title = "Bookmark href", requiredMode = REQUIRED, example = "http://some-other-host-for-jlong.com")
    public String getHref() {
        return bookmark.getHref();
    }

    @NotNull
    @Schema(title = "Bookmark description", requiredMode = REQUIRED, example = "A description for jlong's link")
    public String getDescription() {
        return bookmark.getDescription();
    }

    @NotNull
    @Schema(title = "Bookmark label", requiredMode = REQUIRED, example = "jlongLabel")
    public String getLabel() {
        return bookmark.getLabel();
    }

    @Hidden
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

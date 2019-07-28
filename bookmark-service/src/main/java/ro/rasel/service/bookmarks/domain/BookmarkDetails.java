package ro.rasel.service.bookmarks.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@MappedSuperclass
@ApiModel(description = "User Bookmark")
public class BookmarkDetails {

    @NotNull
    private String href;

    @NotNull
    private String description;

    @NotNull
    private String label;

    BookmarkDetails() {
    }

    public BookmarkDetails(String href, String description, String label) {
        this.href = Objects.requireNonNull(href, "href argument should not be null");
        this.description = Objects.requireNonNull(description, "description argument should not be null");
        this.label = Objects.requireNonNull(label, "label argument should not be null");
    }

    @ApiModelProperty(value = "Bookmark href", required = true)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = Objects.requireNonNull(href, "href argument should not be null");
    }

    @ApiModelProperty(value = "Bookmark description", required = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "description argument should not be null");
    }

    @ApiModelProperty(value = "Bookmark label", required = true)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = Objects.requireNonNull(label, "label argument should not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookmarkDetails that = (BookmarkDetails) o;
        return Objects.equals(href, that.href) &&
                Objects.equals(description, that.description) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(href, description, label);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BookmarkDetails.class.getSimpleName() + "[", "]")
                .add("href='" + href + "'")
                .add("description='" + description + "'")
                .add("label='" + label + "'")
                .toString();
    }
}

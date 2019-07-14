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
        System.out.println();
    }

    public BookmarkDetails(String href, String description, String label) {
        Objects.requireNonNull("href argument should not be null");
        Objects.requireNonNull("description argument should not be null");
        Objects.requireNonNull("label argument should not be null");

        this.href = href;
        this.description = description;
        this.label = label;
    }

    @ApiModelProperty(value = "Bookmark href", required = true)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        Objects.requireNonNull(href, "href argument should not be null");

        this.href = href;
    }

    @ApiModelProperty(value = "Bookmark description", required = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description, "description argument should not be null");

        this.description = description;
    }

    @ApiModelProperty(value = "Bookmark label", required = true)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        Objects.requireNonNull(label, "label argument should not be null");

        this.label = label;
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

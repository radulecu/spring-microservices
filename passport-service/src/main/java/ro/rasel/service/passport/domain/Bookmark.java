package ro.rasel.service.passport.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.domain.bookmark.IBookmark;

import java.util.StringJoiner;

@ApiModel(description = "User Bookmark")
public class Bookmark implements IBookmark {

    private Long id;

    private String userId;

    private String href;

    private String description;

    private String label;

    public Bookmark() {
    }

    @ApiModelProperty(value = "Bookmark id")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Used id")
    public String getUserId() {
        return userId;
    }

    @ApiModelProperty(value = "Bookmark description")
    public String getDescription() {
        return description;
    }

    @ApiModelProperty(value = "Bookmark href")
    public String getHref() {
        return href;
    }

    @ApiModelProperty(value = "Bookmark label")
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bookmark.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("href='" + href + "'")
                .add("description='" + description + "'")
                .add("label='" + label + "'")
                .toString();
    }
}

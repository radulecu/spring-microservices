package ro.rasel.service.bookmarks.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.commons.utils.validators.Validator;
import ro.rasel.commons.utils.validators.Validators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.StringJoiner;

@Entity
@ApiModel(description = "User Bookmark")
public class Bookmark extends BookmarkDetails {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidateor("userId");

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;

    Bookmark() {
    }

    public Bookmark(long id, String userId, String href, String description, String label) {
        this(userId, href, description, label);
        this.id = id;
    }

    public Bookmark(String userId, String href, String description, String label) {
        super(href, description, label);

        USER_ID_VALIDATOR.validate(userId);

        this.userId = userId;
    }

    @ApiModelProperty(value = "Bookmark id")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Used id")
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bookmark.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("href='" + getHref() + "'")
                .add("description='" + getDescription() + "'")
                .add("label='" + getLabel() + "'")
                .toString();
    }
}

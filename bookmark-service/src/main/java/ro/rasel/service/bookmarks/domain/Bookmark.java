package ro.rasel.service.bookmarks.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.commons.utils.validators.Validator;
import ro.rasel.commons.utils.validators.Validators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@ApiModel(description = "User Bookmark")
public class Bookmark extends BookmarkDetails {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;

    @SuppressWarnings("unused") // used by hibernate
    Bookmark() {
    }

    public Bookmark(long id, String userId, String href, String description, String label) {
        this(userId, href, description, label);
        this.id = id;
    }

    public Bookmark(String userId, String href, String description, String label) {
        super(href, description, label);

        this.userId = USER_ID_VALIDATOR.validate(userId);
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bookmark bookmark = (Bookmark) o;
        return super.equals(o) &&
                Objects.equals(id, bookmark.id) &&
                Objects.equals(userId, bookmark.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userId);
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

package ro.rasel.spring.microservices.bookmarkservice.domain;

import ro.rasel.spring.microservices.commons.utils.validators.Validator;
import ro.rasel.spring.microservices.commons.utils.validators.Validators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@SuppressWarnings("JpaObjectClassSignatureInspection")
@Entity
public class Bookmark {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @NotNull
    private String userId;

    @NotNull
    private String href;

    @NotNull
    private String description;

    @NotNull
    private String label;

    private Bookmark() {
    }

    public Bookmark(long id, String userId, String href, String description, String label) {
        this(userId, href, description, label);
        setId(id);
    }

    public Bookmark(String userId, String href, String description, String label) {
        this(href, description, label);
        setUserId(userId);
    }

    public Bookmark(String href, String description, String label) {
        setHref(href);
        setDescription(description);
        setLabel(label);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = USER_ID_VALIDATOR.validate(userId);
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = Objects.requireNonNull(href, "href argument should not be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "description argument should not be null");
    }

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
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(id, bookmark.id) &&
                Objects.equals(userId, bookmark.userId) &&
                Objects.equals(href, bookmark.href) &&
                Objects.equals(description, bookmark.description) &&
                Objects.equals(label, bookmark.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, href, description, label);
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

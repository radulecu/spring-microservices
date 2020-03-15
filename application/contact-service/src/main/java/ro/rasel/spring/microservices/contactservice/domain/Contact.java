package ro.rasel.spring.microservices.contactservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.commons.utils.validators.Validator;
import ro.rasel.spring.microservices.commons.utils.validators.Validators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.StringJoiner;

import static ro.rasel.spring.microservices.contactservice.utils.Constants.EMAIL_PATTERN_STRING;

@SuppressWarnings("JpaObjectClassSignatureInspection")
@Entity
@ApiModel(description = "User Contacts")
public class Contact {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");
    private static final Validator<CharSequence> FIRST_NAME_VALIDATOR =
            Validators.of("firstName", Validators::notNullValidator, Validators::notBlankValidator);
    private static final Validator<CharSequence> LAST_NAME_VALIDATOR =
            Validators.of("lastName", Validators::notNullValidator, Validators::notBlankValidator);
    private static final Validator<CharSequence> EMAIL_VALIDATOR =
            Validators.matchesPatternValidator("email", EMAIL_PATTERN_STRING)
                    .and(Validators.notNullValidator("email"));
    private static final Validator<CharSequence> RELATIONSHIP_VALIDATOR =
            Validators.of("relationship", Validators::notNullValidator, Validators::notBlankValidator);

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN_STRING)
    private String email;

    @NotBlank
    private String relationship;

    private Contact() {
    }

    public Contact(long id, String userId, String firstName, String lastName, String email, String relationship) {
        this(userId, firstName, lastName, email, relationship);
        this.id = id;
    }

    public Contact(String userId, String firstName, String lastName, String email, String relationship) {
        this.userId = USER_ID_VALIDATOR.validate(userId);

        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setRelationship(relationship);
    }

    @ApiModelProperty(value = "Contact id")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Used id")
    public String getUserId() {
        return userId;
    }

    @ApiModelProperty(value = "First name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = FIRST_NAME_VALIDATOR.validate(firstName);
    }

    @ApiModelProperty(value = "Last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = LAST_NAME_VALIDATOR.validate(lastName);
    }

    @ApiModelProperty(value = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = EMAIL_VALIDATOR.validate(email);
    }

    @ApiModelProperty(value = "Relationship")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = RELATIONSHIP_VALIDATOR.validate(relationship);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(userId, contact.userId) &&
                Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(relationship, contact.relationship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, firstName, lastName, email, relationship);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Contact.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("relationship='" + relationship + "'")
                .toString();
    }
}

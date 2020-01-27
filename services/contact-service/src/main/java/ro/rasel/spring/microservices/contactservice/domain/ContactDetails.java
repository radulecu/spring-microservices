package ro.rasel.spring.microservices.contactservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.commons.utils.validators.Validator;
import ro.rasel.spring.microservices.commons.utils.validators.Validators;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.StringJoiner;

@MappedSuperclass
@ApiModel(description = "User Contacts")
public class ContactDetails {
    private static final String EMAIL_PATTERN_STRING = ".+@.+\\..+";
    private static final Validator<CharSequence> FIRST_NAME_VALIDATOR = Validators.notBlankValidator("firstName");
    private static final Validator<CharSequence> LAST_NAME_VALIDATOR = Validators.notBlankValidator("lastName");
    private static final Validator<CharSequence> EMAIL_VALIDATOR =
            Validators.matchesPatternValidator("email", EMAIL_PATTERN_STRING);
    private static final Validator<CharSequence> RELATIONSHIP_VALIDATOR = Validators.notBlankValidator("relationship");

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN_STRING)
    private String email;

    @NotBlank
    private String relationship;

    ContactDetails() {
    }

    public ContactDetails(String firstName, String lastName, String email, String relationship) {
        this.firstName = FIRST_NAME_VALIDATOR.validate(firstName);
        this.lastName = LAST_NAME_VALIDATOR.validate(lastName);
        this.email = EMAIL_VALIDATOR.validate(email);
        this.relationship = RELATIONSHIP_VALIDATOR.validate(relationship);
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
        ContactDetails that = (ContactDetails) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(relationship, that.relationship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, relationship);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactDetails.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("relationship='" + relationship + "'")
                .toString();
    }
}

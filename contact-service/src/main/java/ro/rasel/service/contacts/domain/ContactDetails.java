package ro.rasel.service.contacts.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.commons.utils.validators.Validator;
import ro.rasel.commons.utils.validators.Validators;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.StringJoiner;

@MappedSuperclass
@ApiModel(description = "User Contacts")
public class ContactDetails {
    private static final String EMAIL_PATTERN_STRING = ".+@.+\\..+";
    private static final Validator<CharSequence> FIRST_NAME_VALIDATOR = Validators.notBlankValidateor("firstName");
    private static final Validator<CharSequence> LAST_NAME_VALIDATOR = Validators.notBlankValidateor("lastName");
    private static final Validator<CharSequence> EMAIL_VALIDATOR =
            Validators.matchesPatternValidator("email", EMAIL_PATTERN_STRING);
    private static final Validator<CharSequence> RELATIONSHIP_VALIDATOR = Validators.notBlankValidateor("relationship");

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
        FIRST_NAME_VALIDATOR.validate(firstName);
        LAST_NAME_VALIDATOR.validate(lastName);
        EMAIL_VALIDATOR.validate(email);
        RELATIONSHIP_VALIDATOR.validate(relationship);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.relationship = relationship;
    }

    @ApiModelProperty(value = "First name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        FIRST_NAME_VALIDATOR.validate(firstName);

        this.firstName = firstName;
    }

    @ApiModelProperty(value = "Last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        LAST_NAME_VALIDATOR.validate(lastName);

        this.lastName = lastName;
    }

    @ApiModelProperty(value = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        EMAIL_VALIDATOR.validate(email);

        this.email = email;
    }

    @ApiModelProperty(value = "Relationship")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        RELATIONSHIP_VALIDATOR.validate(relationship);

        this.relationship = relationship;
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

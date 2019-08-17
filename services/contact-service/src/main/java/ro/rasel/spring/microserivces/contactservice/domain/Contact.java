package ro.rasel.spring.microserivces.contactservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microserivces.commons.utils.validators.Validator;
import ro.rasel.spring.microserivces.commons.utils.validators.Validators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.StringJoiner;

@Entity
@ApiModel(description = "User Contacts")
public class Contact extends ContactDetails {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;

    public Contact() {
    }

    public Contact(
            Long id, String userId, String firstName, String lastName, String email,
            String relationship) {
        this(userId, firstName, lastName, email, relationship);
        this.id = id;
    }

    public Contact(String userId, String firstName, String lastName, String email, String relationship) {
        super(firstName, lastName, email, relationship);

        this.userId = USER_ID_VALIDATOR.validate(userId);
    }

    @ApiModelProperty(value = "Contact id")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Used id")
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Contact.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("relationship='" + getRelationship() + "'")
                .add("firstName='" + getFirstName() + "'")
                .add("lastName='" + getLastName() + "'")
                .add("email='" + getEmail() + "'")
                .toString();
    }
}

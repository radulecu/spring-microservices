package ro.rasel.spring.microservices.contactservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.commons.utils.validators.Validator;
import ro.rasel.spring.microservices.commons.utils.validators.Validators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
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
            long id, String userId, String firstName, String lastName, String email, String relationship) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return super.equals(o) &&
                Objects.equals(id, contact.id) &&
                Objects.equals(userId, contact.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userId);
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

package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.commons.utils.validators.Validator;
import ro.rasel.spring.microservices.commons.utils.validators.Validators;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel(description = "User Contacts")
public class ContactResponse extends ContactDetails<PhoneNumberResponse, AddressResponse> {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");

    private Long id;

    @NotBlank
    private String userId;

    public ContactResponse() {
    }

    @ConstructorProperties({"id", "userId", "firstName", "lastName", "email", "relationship", "phoneNumbers", "addresses"})
    public ContactResponse(
            long id, String userId, String firstName, String lastName, String email, String relationship,
            List<PhoneNumberResponse> phoneNumbers, List<AddressResponse> addresses) {
        this(userId, firstName, lastName, email, relationship, phoneNumbers, addresses);
        this.id = id;
    }

    public ContactResponse(
            String userId, String firstName, String lastName, String email, String relationship,
            List<PhoneNumberResponse> phoneNumbers, List<AddressResponse> addresses) {
        super(firstName, lastName, email, relationship, phoneNumbers, addresses);

        this.userId = USER_ID_VALIDATOR.validate(userId);
    }

    @ApiModelProperty(value = "Contact id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(value = "Used id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ContactResponse that = (ContactResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactResponse.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .toString()
                + " " + super.toString();
    }
}

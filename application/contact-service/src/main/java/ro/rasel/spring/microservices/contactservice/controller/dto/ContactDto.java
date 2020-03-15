package ro.rasel.spring.microservices.contactservice.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.spring.microservices.commons.utils.validators.Validator;
import ro.rasel.spring.microservices.commons.utils.validators.Validators;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

@ApiModel(description = "User Contacts")
public class ContactDto extends ContactDetailsDto {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");

    private Long id;

    @NotBlank
    private String userId;

    ContactDto() {
    }

    @ConstructorProperties({"id", "userId", "firstName", "lastName", "email", "relationship"})
    public ContactDto(
            long id, String userId, String firstName, String lastName, String email, String relationship) {
        this(userId, firstName, lastName, email, relationship);
        this.id = id;
    }

    public ContactDto(String userId, String firstName, String lastName, String email, String relationship) {
        super(firstName, lastName, email, relationship);

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
        ContactDto contactDto = (ContactDto) o;
        return super.equals(o) &&
                Objects.equals(id, contactDto.id) &&
                Objects.equals(userId, contactDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("relationship='" + getRelationship() + "'")
                .add("firstName='" + getFirstName() + "'")
                .add("lastName='" + getLastName() + "'")
                .add("email='" + getEmail() + "'")
                .toString();
    }
}

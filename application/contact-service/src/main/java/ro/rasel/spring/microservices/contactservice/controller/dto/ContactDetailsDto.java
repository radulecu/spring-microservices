package ro.rasel.spring.microservices.contactservice.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.beans.ConstructorProperties;
import java.util.Objects;
import java.util.StringJoiner;

import static ro.rasel.spring.microservices.contactservice.utils.Constants.EMAIL_PATTERN_STRING;

@ApiModel(description = "User Contacts")
public class ContactDetailsDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN_STRING)
    private String email;

    @NotBlank
    private String relationship;

    ContactDetailsDto() {
    }

    @ConstructorProperties({"firstName", "lastName", "email", "relationship"})
    public ContactDetailsDto(String firstName, String lastName, String email, String relationship) {
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
        this.firstName = firstName;
    }

    @ApiModelProperty(value = "Last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ApiModelProperty(value = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ApiModelProperty(value = "Relationship")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactDetailsDto that = (ContactDetailsDto) o;
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
        return new StringJoiner(", ", ContactDetailsDto.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("relationship='" + relationship + "'")
                .toString();
    }
}

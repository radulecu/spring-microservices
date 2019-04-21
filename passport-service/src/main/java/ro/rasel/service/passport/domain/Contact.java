package ro.rasel.service.passport.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ro.rasel.domain.contact.IContact;

import java.util.StringJoiner;

@ApiModel(description = "User Contacts")
public class Contact implements IContact {

    private Long id;

    private String userId;

    private String relationship;

    private String firstName;

    private String lastName;

    private String email;

    @ApiModelProperty(value = "Contact id")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Used id")
    public String getUserId() {
        return userId;
    }

    @ApiModelProperty(value = "Relationship")
    public String getRelationship() {
        return relationship;
    }

    @ApiModelProperty(value = "First name")
    public String getFirstName() {
        return firstName;
    }

    @ApiModelProperty(value = "Last name")
    public String getLastName() {
        return lastName;
    }

    @ApiModelProperty(value = "Email")
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Contact.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("relationship='" + relationship + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .toString();
    }
}

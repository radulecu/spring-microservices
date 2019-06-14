package ro.rasel.service.contacts.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.StringJoiner;

@Entity
@ApiModel(description = "User Contacts")
public class Contact {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String relationship;

    private String firstName;

    private String lastName;

    private String email;

    public Contact() {
    }

    public Contact(String userId, String firstName, String lastName, String email,
            String relationship) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.relationship = relationship;
    }

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

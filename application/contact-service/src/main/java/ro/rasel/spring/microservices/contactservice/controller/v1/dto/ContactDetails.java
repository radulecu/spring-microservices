package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static ro.rasel.spring.microservices.contactservice.utils.Constants.EMAIL_PATTERN_STRING;

public class ContactDetails<P extends PhoneNumberDto, A extends AddressDto> {
    private String firstName;
    private String lastName;
    private String email;
    private String relationship;
    private List<P> phoneNumbers;
    private List<A> addresses;

    public ContactDetails() {
    }

    public ContactDetails(
            String firstName, String lastName, String email, String relationship, List<P> phoneNumbers,
            List<A> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.relationship = relationship;
        this.phoneNumbers = phoneNumbers;
        this.addresses = addresses;
    }

    @NotBlank
    @ApiModelProperty(value = "First name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @ApiModelProperty(value = "Last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ApiModelProperty(value = "Email")
    @Pattern(regexp = EMAIL_PATTERN_STRING)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank
    @ApiModelProperty(value = "Relationship")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public List<P> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<P> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<A> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<A> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactDetails<?, ?> that = (ContactDetails<?, ?>) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(relationship, that.relationship) &&
                Objects.equals(phoneNumbers, that.phoneNumbers) &&
                Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, relationship, phoneNumbers, addresses);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactDetails.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("relationship='" + relationship + "'")
                .add("phoneNumbers=" + phoneNumbers)
                .add("addresses=" + addresses)
                .toString();
    }
}

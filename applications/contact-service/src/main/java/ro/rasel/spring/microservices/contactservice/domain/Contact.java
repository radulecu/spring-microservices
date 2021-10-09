package ro.rasel.spring.microservices.contactservice.domain;

import io.swagger.annotations.ApiModel;
import ro.rasel.spring.microservices.common.utils.validators.Validator;
import ro.rasel.spring.microservices.common.utils.validators.Validators;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static ro.rasel.spring.microservices.contactservice.utils.Constants.EMAIL_PATTERN_STRING;

@SuppressWarnings("JpaObjectClassSignatureInspection")
@Entity
public class Contact {
    private static final Validator<CharSequence> USER_ID_VALIDATOR = Validators.notBlankValidator("userId");
    private static final Validator<CharSequence> FIRST_NAME_VALIDATOR =
            Validators.of("firstName", Validators::notNullValidator, Validators::notBlankValidator);
    private static final Validator<CharSequence> LAST_NAME_VALIDATOR =
            Validators.of("lastName", Validators::notNullValidator, Validators::notBlankValidator);
    private static final Validator<CharSequence> EMAIL_VALIDATOR =
            Validators.matchesPatternValidator("email", EMAIL_PATTERN_STRING)
                    .and(Validators.notNullValidator("email"));
    private static final Validator<CharSequence> RELATIONSHIP_VALIDATOR =
            Validators.of("relationship", Validators::notNullValidator, Validators::notBlankValidator);

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN_STRING)
    private String email;

    @NotBlank
    private String relationship;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id",referencedColumnName="id")
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id",referencedColumnName="id")
    private List<Address> addresses = new ArrayList<>();

    private Contact() {
    }

    public Contact(
            Long id, String userId, String firstName, String lastName, String email, String relationship,
            List<PhoneNumber> phoneNumbers, List<Address> addresses) {
        this(userId, firstName, lastName, email, relationship, phoneNumbers, addresses);
        this.id = id;
    }

    public Contact(
            String userId, String firstName, String lastName, String email, String relationship,
            List<PhoneNumber> phoneNumbers, List<Address> addresses) {
        this.userId = USER_ID_VALIDATOR.validate(userId);

        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setRelationship(relationship);
        setPhoneNumbers(phoneNumbers);
        setAddresses(addresses);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

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
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(userId, contact.userId) &&
                Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(phoneNumbers, contact.phoneNumbers) &&
                Objects.equals(addresses, contact.addresses) &&
                Objects.equals(relationship, contact.relationship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, firstName, lastName, email, phoneNumbers, addresses, relationship);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Contact.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId='" + userId + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("phoneNumbers=" + phoneNumbers)
                .add("addresses=" + addresses)
                .add("relationship='" + relationship + "'")
                .toString();
    }

    public static final class ContactBuilder {
        private Contact contact;

        private ContactBuilder() {
            contact = new Contact();
        }

        public static ContactBuilder aContact() {
            return new ContactBuilder();
        }

        public ContactBuilder withId(Long id) {
            contact.setId(id);
            return this;
        }

        public ContactBuilder withUserId(String userId) {
            contact.setUserId(userId);
            return this;
        }

        public ContactBuilder withFirstName(String firstName) {
            contact.setFirstName(firstName);
            return this;
        }

        public ContactBuilder withLastName(String lastName) {
            contact.setLastName(lastName);
            return this;
        }

        public ContactBuilder withEmail(String email) {
            contact.setEmail(email);
            return this;
        }

        public ContactBuilder withPhoneNumbers(List<PhoneNumber> phoneNumbers) {
            contact.setPhoneNumbers(phoneNumbers);
            return this;
        }

        public ContactBuilder withAddresses(List<Address> addresses) {
            contact.setAddresses(addresses);
            return this;
        }

        public ContactBuilder withRelationship(String relationship) {
            contact.setRelationship(relationship);
            return this;
        }

        public Contact build() {
            return contact;
        }
    }
}

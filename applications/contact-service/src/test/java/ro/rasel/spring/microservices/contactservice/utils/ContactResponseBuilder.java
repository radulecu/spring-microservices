package ro.rasel.spring.microservices.contactservice.utils;

import ro.rasel.spring.microservices.contactservice.controller.v1.dto.AddressResponse;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactResponse;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.PhoneNumberDetailsResponse;

import java.util.List;

public final class ContactResponseBuilder {
    private ContactResponse contactResponse;

    private ContactResponseBuilder() {
        contactResponse = new ContactResponse();
    }

    public static ContactResponseBuilder aContactResponse() {
        return new ContactResponseBuilder();
    }

    public ContactResponseBuilder withFirstName(String firstName) {
        contactResponse.setFirstName(firstName);
        return this;
    }

    public ContactResponseBuilder withLastName(String lastName) {
        contactResponse.setLastName(lastName);
        return this;
    }

    public ContactResponseBuilder withEmail(String email) {
        contactResponse.setEmail(email);
        return this;
    }

    public ContactResponseBuilder withRelationship(String relationship) {
        contactResponse.setRelationship(relationship);
        return this;
    }

    public ContactResponseBuilder withPhoneNumbers(List<PhoneNumberDetailsResponse> phoneNumbers) {
        contactResponse.setPhoneNumbers(phoneNumbers);
        return this;
    }

    public ContactResponseBuilder withAddresses(List<AddressResponse> addresses) {
        contactResponse.setAddresses(addresses);
        return this;
    }

    public ContactResponseBuilder withId(Long id) {
        contactResponse.setId(id);
        return this;
    }

    public ContactResponseBuilder withUserId(String userId) {
        contactResponse.setUserId(userId);
        return this;
    }

    public ContactResponse build() {
        return contactResponse;
    }
}

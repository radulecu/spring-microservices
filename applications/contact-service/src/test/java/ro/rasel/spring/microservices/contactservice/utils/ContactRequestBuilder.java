package ro.rasel.spring.microservices.contactservice.utils;

import ro.rasel.spring.microservices.contactservice.controller.v1.dto.AddressRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.PhoneNumberDetailsRequest;

import java.util.List;

public final class ContactRequestBuilder {
    private ContactRequest contactRequest;

    private ContactRequestBuilder() {
        contactRequest = new ContactRequest();
    }

    public static ContactRequestBuilder aContactRequest() {
        return new ContactRequestBuilder();
    }

    public ContactRequestBuilder withFirstName(String firstName) {
        contactRequest.setFirstName(firstName);
        return this;
    }

    public ContactRequestBuilder withLastName(String lastName) {
        contactRequest.setLastName(lastName);
        return this;
    }

    public ContactRequestBuilder withEmail(String email) {
        contactRequest.setEmail(email);
        return this;
    }

    public ContactRequestBuilder withRelationship(String relationship) {
        contactRequest.setRelationship(relationship);
        return this;
    }

    public ContactRequestBuilder withPhoneNumbers(List<PhoneNumberDetailsRequest> phoneNumbers) {
        contactRequest.setPhoneNumbers(phoneNumbers);
        return this;
    }

    public ContactRequestBuilder withAddresses(List<AddressRequest> addresses) {
        contactRequest.setAddresses(addresses);
        return this;
    }

    public ContactRequest build() {
        return contactRequest;
    }
}

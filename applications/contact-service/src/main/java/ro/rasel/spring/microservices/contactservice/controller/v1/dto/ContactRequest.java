package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.annotations.ApiModel;

import java.beans.ConstructorProperties;
import java.util.List;

@ApiModel(description = "User Contacts")
public class ContactRequest extends ContactDetails<PhoneNumberRequest, AddressRequest> {
    public ContactRequest() {
    }

    @ConstructorProperties({"firstName", "lastName", "email", "relationship", "phoneNumbers", "addresses"})
    public ContactRequest(
            String firstName, String lastName, String email, String relationship, List<PhoneNumberRequest> phoneNumbers,
            List<AddressRequest> addresses) {
        super(firstName, lastName, email, relationship, phoneNumbers, addresses);
    }
}

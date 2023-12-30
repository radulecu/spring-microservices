package ro.rasel.spring.microservices.contactservice.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.beans.ConstructorProperties;
import java.util.List;

@Schema(description = "User contacts")
public class ContactRequest extends ContactDetailsDto<PhoneNumberDetailsRequest, AddressRequest> {
    public ContactRequest() {
    }

    @ConstructorProperties({"firstName", "lastName", "email", "relationship", "phoneNumbers", "addresses"})
    public ContactRequest(
            String firstName, String lastName, String email, String relationship, List<PhoneNumberDetailsRequest> phoneNumbers,
            List<AddressRequest> addresses) {
        super(firstName, lastName, email, relationship, phoneNumbers, addresses);
    }
}

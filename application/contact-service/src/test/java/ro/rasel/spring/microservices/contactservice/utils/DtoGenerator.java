package ro.rasel.spring.microservices.contactservice.utils;

import ro.rasel.spring.microservices.contactservice.controller.v1.dto.AddressRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.AddressResponse;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.PhoneNumberRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.PhoneNumberResponse;

import java.util.Arrays;

import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.ADDRESS_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.CONTACT_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.COUNTRY;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.COUNTRY2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.EMAIL;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.ENTRANCE_NUMBER;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.FIRST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.FLAT_NUMBER;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.LAST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.PHONE_NUMBER;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.PHONE_NUMBER2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.PHONE_NUMBER_DESCRIPTION;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.PHONE_NUMBER_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.PHONE_NUMBER_ID2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.RELATIONSHIP;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.STREET;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.STREET2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.STREET_NUMBER;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.STREET_NUMBER2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.TOWN;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.TOWN2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.USER_ID;

public class DtoGenerator {
    public static ContactRequestBuilder createContactRequestBuilder() {
        return ContactRequestBuilder.aContactRequest()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(EMAIL)
                .withRelationship(RELATIONSHIP)
                .withPhoneNumbers(
                        Arrays.asList(createPhoneNumberRequest(), createPhoneNumberRequestWithoutDescription()))
                .withAddresses(Arrays.asList(createAddressRequest(), createAddressRequestWithMandatoryFieldsOnly()));
    }

    private static PhoneNumberRequest createPhoneNumberRequest() {
        return new PhoneNumberRequest(PHONE_NUMBER, USER_ID + " " + FIRST_NAME + " " + PHONE_NUMBER_DESCRIPTION);
    }

    private static PhoneNumberRequest createPhoneNumberRequestWithoutDescription() {
        return new PhoneNumberRequest(PHONE_NUMBER2, null);
    }

    private static AddressRequest createAddressRequest() {
        return new AddressRequest(COUNTRY, TOWN, STREET, STREET_NUMBER, ENTRANCE_NUMBER, FLAT_NUMBER);
    }

    private static AddressRequest createAddressRequestWithMandatoryFieldsOnly() {
        return new AddressRequest(COUNTRY2, TOWN2, STREET2, STREET_NUMBER2, null, null);
    }

    public static ContactResponseBuilder createContactResponseBuilder() {
        return createContactResponseBuilder(CONTACT_ID);
    }

    public static ContactResponseBuilder createContactResponseBuilder(Long contactId) {
        return ContactResponseBuilder.aContactResponse()
                .withId(contactId)
                .withUserId(USER_ID)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(EMAIL)
                .withRelationship(RELATIONSHIP)
                .withPhoneNumbers(
                        Arrays.asList(createPhoneNumberResponse(), createPhoneNumberResponseWithoutDescription()))
                .withAddresses(Arrays.asList(createAddressResponse(), createAddressResponseWithMandatoryFieldsOnly()));
    }

    private static PhoneNumberResponse createPhoneNumberResponse() {
        return new PhoneNumberResponse(PHONE_NUMBER_ID, PHONE_NUMBER,
                USER_ID + " " + FIRST_NAME + " " + PHONE_NUMBER_DESCRIPTION);
    }

    private static PhoneNumberResponse createPhoneNumberResponseWithoutDescription() {
        return new PhoneNumberResponse(PHONE_NUMBER_ID2, PHONE_NUMBER2, null);
    }

    private static AddressResponse createAddressResponse() {
        return new AddressResponse(ADDRESS_ID, COUNTRY, TOWN, STREET, STREET_NUMBER, ENTRANCE_NUMBER, FLAT_NUMBER);
    }

    private static AddressResponse createAddressResponseWithMandatoryFieldsOnly() {
        return new AddressResponse(PHONE_NUMBER_ID2, COUNTRY2, TOWN2, STREET2, STREET_NUMBER2, null, null);
    }
}

package ro.rasel.spring.microservices.contactservice.utils;

import ro.rasel.spring.microservices.contactservice.domain.Address;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.domain.PhoneNumber;

import java.util.Arrays;

import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.ADDRESS_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.ADDRESS_ID2;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.CONTACT_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.COUNTRY;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.COUNTRY2;
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

public class DomainGenerator {
    public static Contact createContact() {
        return createContact(false);
    }

    public static Contact createContact(boolean withIds) {
        return createContact(CONTACT_ID, USER_ID, "", withIds);
    }

    public static Contact createContact(String userId) {
        return createContact(null, userId, "", false);
    }

    public static Contact createContact(
            Long contactId, String userId, String suffix, boolean withIds) {
        return createContactBuilder(contactId, userId, suffix, withIds).build();
    }

    private static Contact.ContactBuilder createContactBuilder(
            Long contactId, String userId, String suffix, boolean withIds) {
        return Contact.ContactBuilder.aContact()
                .withId(contactId)
                .withUserId(userId)
                .withFirstName(FIRST_NAME + suffix)
                .withLastName(LAST_NAME + suffix)
                .withEmail(String.format("%s@email.com", FIRST_NAME.toLowerCase() + suffix))
                .withRelationship(RELATIONSHIP + suffix)
                .withPhoneNumbers(
                        Arrays.asList(createPhoneNumber(withIds), createPhoneNumberWithoutDescription(withIds)))
                .withAddresses(Arrays.asList(createAddress(withIds), createAddressWithMandatoryFieldsOnly(withIds)));
    }

    private static PhoneNumber createPhoneNumber(boolean withIds) {
        return createPhoneNumber(withIds ? PHONE_NUMBER_ID : null);
    }

    private static PhoneNumber createPhoneNumber(Long phoneNumberId) {
        return new PhoneNumber(phoneNumberId, PHONE_NUMBER,
                USER_ID + " " + FIRST_NAME + " " + PHONE_NUMBER_DESCRIPTION);
    }

    private static PhoneNumber createPhoneNumberWithoutDescription(boolean withIds) {
        return createPhoneNumberWithoutDescription(withIds ? PHONE_NUMBER_ID2 : null);
    }

    private static PhoneNumber createPhoneNumberWithoutDescription(Long phoneNumberId) {
        return new PhoneNumber(phoneNumberId, PHONE_NUMBER2, null);
    }

    private static Address createAddress(boolean withIds) {
        return createAddress(withIds ? ADDRESS_ID : null, withIds ? CONTACT_ID : null);
    }

    private static Address createAddress(Long addressId, Long contactId) {
        return new Address(addressId, contactId, COUNTRY, TOWN, STREET, STREET_NUMBER, ENTRANCE_NUMBER, FLAT_NUMBER);
    }

    private static Address createAddressWithMandatoryFieldsOnly(boolean withIds) {
        return createAddressWithMandatoryFieldsOnly(withIds ? ADDRESS_ID2 : null, withIds ? CONTACT_ID : null);
    }

    private static Address createAddressWithMandatoryFieldsOnly(Long addressId, Long contactId) {
        return new Address(addressId, contactId, COUNTRY2, TOWN2, STREET2, STREET_NUMBER2, null, null);
    }
}

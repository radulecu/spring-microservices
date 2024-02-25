package ro.rasel.spring.microservices.passportservice.integration.controller;

import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.AddressResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import ro.rasel.spring.microservices.api.contact.data.PhoneNumberDetailsResponse;

import java.util.Collections;
import java.util.List;

public class RestControllerTestUtils {
    public static final List<BookmarkResponse> BOOKMARK_RESPONSE_LIST = Collections.singletonList(new BookmarkResponse()
            .id(12345L)
            .userId("userID")
            .description("description")
            .href("href")
            .label("label")
    );
    public static final List<ContactResponse> CONTACT_RESPONSE_LIST = Collections.singletonList(new ContactResponse()
            .id(121L)
            .userId("userID")
            .firstName("FisrtName")
            .lastName("Lastname")
            .relationship("Relationship")
            .addAddressesItem(new AddressResponse()
                    .id(12L)
                    .country("Country")
                    .town("Town")
                    .street("Street"))
            .addPhoneNumbersItem(new PhoneNumberDetailsResponse()
                    .number("number")
                    .description("description")));
}

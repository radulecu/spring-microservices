package ro.rasel.spring.microservices.passportservice.integration.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.client.MockRestServiceServer;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.AddressResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import ro.rasel.spring.microservices.api.contact.data.PhoneNumberDetails;
import ro.rasel.spring.microservices.passportservice.client.AsyncIntegrationClient;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;
import rx.Observable;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AsyncPassportRestControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    private MockRestServiceServer mockServer;

    @Test
    public void shouldGetPassport() {
        testRestTemplate.getForEntity("/v1/async/users/{userId}/passport", PassportResponse.class, "test");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public AsyncIntegrationClient asyncIntegrationClient() {
            return new AsyncIntegrationClient() {
                @Override
                public Observable<Collection<BookmarkResponse>> getBookmarks(
                        String userId) {
                    return Observable.just(
                            Collections.singletonList(new BookmarkResponse()
                                    .id(12345L)
                                    .userId("userID")
                                    .description("description")
                                    .href("href")
                                    .label("label")
                            )
                    );
                }

                @Override
                public Observable<Collection<ContactResponse>> getContacts(
                        String userId) {
                    return Observable.just(
                            Collections.singletonList(new ContactResponse()
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
                                    .addPhoneNumbersItem(new PhoneNumberDetails()
                                            .number("number")
                                            .description("description")))
                    );
                }
            };
        }
    }

}
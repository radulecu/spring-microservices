package ro.rasel.spring.microservices.passportservice.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncResult;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.AddressResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import ro.rasel.spring.microservices.api.contact.data.PhoneNumberDetailsResponse;
import ro.rasel.spring.microservices.passportservice.client.AsyncIntegrationClient;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AsyncPassportRestControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

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
                public Future<Collection<BookmarkResponse>> getBookmarks(
                        String userId) {
                    return AsyncResult.forValue(
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
                public Future<Collection<ContactResponse>> getContacts(
                        String userId) {
                    return AsyncResult.forValue(
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
                                    .addPhoneNumbersItem(new PhoneNumberDetailsResponse()
                                            .number("number")
                                            .description("description")))
                    );
                }
            };
        }
    }

}
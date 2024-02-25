package ro.rasel.spring.microservices.passportservice.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import ro.rasel.spring.microservices.passportservice.client.AsyncIntegrationClient;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;

import java.util.Collection;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AsyncPassportRestControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetPassport() {
        ResponseEntity<PassportResponse> responseEntity =
                testRestTemplate.getForEntity("/v1/async/users/{userId}/passport", PassportResponse.class, "test");

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PassportResponse passportResponse = new PassportResponse("test", RestControllerTestUtils.BOOKMARK_RESPONSE_LIST, RestControllerTestUtils.CONTACT_RESPONSE_LIST);
        assertThat(responseEntity.getBody(), is(passportResponse));
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
                    return AsyncResult.forValue(RestControllerTestUtils.BOOKMARK_RESPONSE_LIST);
                }

                @Override
                public Future<Collection<ContactResponse>> getContacts(
                        String userId) {
                    return AsyncResult.forValue(RestControllerTestUtils.CONTACT_RESPONSE_LIST);
                }
            };
        }
    }

}
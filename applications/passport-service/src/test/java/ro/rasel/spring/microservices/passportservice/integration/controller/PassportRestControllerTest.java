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
import ro.rasel.spring.microservices.api.bookmark.data.BookmarkResponse;
import ro.rasel.spring.microservices.api.contact.data.ContactResponse;
import ro.rasel.spring.microservices.passportservice.client.IntegrationClient;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PassportRestControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetPassport() {
        ResponseEntity<PassportResponse> responseEntity =
                testRestTemplate.getForEntity("/v1/users/{userId}/passport", PassportResponse.class, "test");

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PassportResponse passportResponse = new PassportResponse("test", RestControllerTestUtils.BOOKMARK_RESPONSE_LIST, RestControllerTestUtils.CONTACT_RESPONSE_LIST);
        assertThat(responseEntity.getBody(), is(passportResponse));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public IntegrationClient asyncIntegrationClient() {
            return new IntegrationClient() {
                @Override
                public Collection<BookmarkResponse> getBookmarks(
                        String userId) {
                    return RestControllerTestUtils.BOOKMARK_RESPONSE_LIST;
                }

                @Override
                public Collection<ContactResponse> getContacts(
                        String userId) {
                    return RestControllerTestUtils.CONTACT_RESPONSE_LIST;
                }
            };
        }
    }

}
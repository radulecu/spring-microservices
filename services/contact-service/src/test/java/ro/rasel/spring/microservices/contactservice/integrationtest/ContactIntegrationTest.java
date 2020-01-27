package ro.rasel.spring.microservices.contactservice.integrationtest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.contactservice.dao.ContactRepository;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.domain.ContactDetails;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.EMAIL;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.FIRST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.LAST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.RELATIONSHIP;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.USER_ID;

@SuppressWarnings({"ConstantConditions", "OptionalGetWithoutIsPresent", "SameParameterValue"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ContactIntegrationTest {
    private static final String CONTACTS_ENDPOINT = "/v1/users/{userId}/contacts";
    private static final String CONTACT_ENDPOINT = "/v1/users/{userId}/contacts/{contactId}";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ContactRepository contactRepository;

    @BeforeEach
    void before() {
        contactRepository.deleteAll();
    }

    @Test
    void shouldGetContactCollectionWhenContactsExist() {
        final Contact contact = initContactInDb(USER_ID);

        final ResponseEntity<List<Contact>> result = testRestTemplate
                .exchange(CONTACTS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<Contact>>() {
                        }, USER_ID);

        assertThat(result.getBody().get(0), is(contact));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyContactsCollectionWhenContactsDoesNotExist() {

        final ResponseEntity<List<Contact>> result = testRestTemplate
                .exchange(CONTACTS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<Contact>>() {
                        }, USER_ID);

        assertThat(result.getBody(), is((List<Contact>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetContactWhenContactExist() {
        final Contact contact = initContactInDb(USER_ID);
        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, Contact.class,
                        USER_ID, contact.getId());

        assertThat(result.getBody(), is(contact));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoContactWhenContactsDoesNotExist() {
        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, Contact.class,
                        USER_ID, 3);

        assertThat(result.getBody(), is((Contact) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateContact() {
        final ContactDetails contactDetails = new ContactDetails(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACTS_ENDPOINT, HttpMethod.POST, new HttpEntity<>(contactDetails),
                        Contact.class, USER_ID);

        final Contact contact = result.getBody();

        assertThat(contact.getUserId(), is(USER_ID));
        assertThat(contact.getFirstName(), is(FIRST_NAME));
        assertThat(contact.getLastName(), is(LAST_NAME));
        assertThat(contact.getEmail(), is(EMAIL));
        assertThat(contact.getRelationship(), is(RELATIONSHIP));

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

        assertThat(getContactFromDb(contact.getId(), USER_ID).get(), is(contact));

    }

    @Test
    void shouldUpdateContactWhenContactExists() {
        final long contactId = initContactInDb(USER_ID, "0").getId();
        final ContactDetails contactDetails = new ContactDetails(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contact =
                new Contact(contactId, USER_ID, contactDetails.getFirstName(), contactDetails.getLastName(),
                        contactDetails.getEmail(), contactDetails.getRelationship());

        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(contactDetails), Contact.class, USER_ID, contactId);

        assertThat(result.getBody(), is(contact));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateContactAndContactDoesNotExists() {
        final ContactDetails contactDetails = new ContactDetails(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(contactDetails), Contact.class, USER_ID, 1);

        assertThat(result.getBody(), is((Contact) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteContactWhenContactExists() {
        final long contactId = initContactInDb(USER_ID).getId();

        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, Contact.class, USER_ID, contactId);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteContactAndContactDoesNotExists() {
        final ResponseEntity<Contact> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, Contact.class, USER_ID, 1);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private Contact initContactInDb(String userId) {
        return contactRepository.save(createContact(userId));
    }

    private Contact initContactInDb(String userId, String suffix) {
        return contactRepository.save(createContact(userId, suffix));
    }

    private Optional<Contact> getContactFromDb(long id, String userId) {
        return contactRepository.findByIdAndUserId(id, userId);
    }

    private Contact createContact(String userId) {
        return createContact(userId, "");
    }

    private Contact createContact(String userId, String suffix) {
        return new Contact(userId,
                FIRST_NAME + suffix,
                LAST_NAME + suffix,
                String.format("%s@email.com", FIRST_NAME.toLowerCase() + suffix),
                "friend" + suffix);
    }
}
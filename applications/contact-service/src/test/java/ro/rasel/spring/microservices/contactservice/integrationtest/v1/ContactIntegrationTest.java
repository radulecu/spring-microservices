package ro.rasel.spring.microservices.contactservice.integrationtest.v1;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.AddressResponse;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactResponse;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.PhoneNumberDetailsResponse;
import ro.rasel.spring.microservices.contactservice.dao.ContactRepository;
import ro.rasel.spring.microservices.contactservice.domain.Address;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.domain.PhoneNumber;
import ro.rasel.spring.microservices.contactservice.utils.DomainGenerator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ro.rasel.spring.microservices.contactservice.utils.DomainGenerator.createContact;
import static ro.rasel.spring.microservices.contactservice.utils.DtoGenerator.createContactRequestBuilder;
import static ro.rasel.spring.microservices.contactservice.utils.DtoGenerator.createContactResponseBuilder;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.EMAIL;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.FIRST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.LAST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.RELATIONSHIP;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.USER_ID;

@SuppressWarnings({"ConstantConditions", "SameParameterValue", "OptionalGetWithoutIsPresent"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(ContactIntegrationTest.DbUtils.class)
class ContactIntegrationTest {
    private static final String CONTACTS_ENDPOINT = "/v1/users/{userId}/contacts";
    private static final String CONTACT_ENDPOINT = "/v1/users/{userId}/contacts/{contactId}";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    DbUtils dbUtils;

    @BeforeEach
    void before() {
        dbUtils.clearDb();
    }

    @Test
    void shouldGetContactCollectionWhenContactsExist() {
        final ContactResponse expectedContactResponse = dbUtils.initContactInDb(USER_ID);

        final ResponseEntity<List<ContactResponse>> result = testRestTemplate
                .exchange(CONTACTS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<ContactResponse>>() {
                        }, USER_ID);

        assertThat(result.getBody().get(0), is(expectedContactResponse));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyContactsCollectionWhenContactsDoesNotExist() {
        final ResponseEntity<List<ContactResponse>> result = testRestTemplate
                .exchange(CONTACTS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<ContactResponse>>() {
                        }, USER_ID);

        assertThat(result.getBody(), is((List<ContactResponse>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetContactWhenContactExist() {
        final ContactResponse expectedContactResponse = dbUtils.initContactInDb(USER_ID);
        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, ContactResponse.class,
                        USER_ID, expectedContactResponse.getId());

        assertThat(result.getBody(), is(expectedContactResponse));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoContactWhenContactsDoesNotExist() {
        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, ContactResponse.class,
                        USER_ID, 3);

        assertThat(result.getBody(), is((ContactResponse) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateContact() {
        final ContactRequest contactDetails = createContactRequestBuilder().build();

        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACTS_ENDPOINT, HttpMethod.POST, new HttpEntity<>(contactDetails),
                        ContactResponse.class, USER_ID);

        final ContactResponse contact = result.getBody();

        assertThat(contact.getUserId(), is(USER_ID));
        assertThat(contact.getFirstName(), is(FIRST_NAME));
        assertThat(contact.getLastName(), is(LAST_NAME));
        assertThat(contact.getEmail(), is(EMAIL));
        assertThat(contact.getRelationship(), is(RELATIONSHIP));

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

        assertThat(dbUtils.getContactFromDb(contact.getId(), USER_ID).get(), is(contact));

    }

    @Test
    void shouldUpdateContactWhenContactExists() {
        final long contactId = dbUtils.initContactInDb(USER_ID, "0").getId();
        final ContactRequest contactRequest = createContactRequestBuilder().build();
        final ContactResponse expectedContactResponse = createContactResponseBuilder().build();

        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(contactRequest), ContactResponse.class, USER_ID, contactId);

        final ContactResponse contactResponse = result.getBody();
        alignIds(contactResponse, expectedContactResponse);

        assertThat(contactResponse, is(expectedContactResponse));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateContactAndContactDoesNotExists() {
        final ContactRequest contactDetails = createContactRequestBuilder().build();

        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(contactDetails), ContactResponse.class, USER_ID, 1);

        assertThat(result.getBody(), is((ContactResponse) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteContactWhenContactExists() {
        final long contactId = dbUtils.initContactInDb(USER_ID).getId();

        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, ContactResponse.class, USER_ID, contactId);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteContactAndContactDoesNotExists() {
        final ResponseEntity<ContactResponse> result = testRestTemplate
                .exchange(CONTACT_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, ContactResponse.class, USER_ID, 1);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private void alignIds(ContactResponse contactResponse, ContactResponse expectedContactResponse) {
        expectedContactResponse.setId(contactResponse.getId());
        for (int i = 0; i < contactResponse.getPhoneNumbers().size() &&
                i < expectedContactResponse.getPhoneNumbers().size(); i++) {
            expectedContactResponse.getPhoneNumbers().get(i).setId(
                    contactResponse.getPhoneNumbers().get(i).getId()
            );
        }
        for (int i = 0; i < contactResponse.getAddresses().size() &&
                i < expectedContactResponse.getAddresses().size(); i++) {
            expectedContactResponse.getAddresses().get(i).setId(
                    contactResponse.getAddresses().get(i).getId()
            );
        }

    }

    @TestComponent
    @Transactional
    public static class DbUtils {
        @Autowired
        ContactRepository contactRepository;

        public void clearDb() {
            contactRepository.deleteAll();
        }

        public ContactResponse initContactInDb(String userId) {
            return toContactDto(contactRepository.save(createContact(userId)));
        }

        public ContactResponse initContactInDb(String userId, String suffix) {
            return toContactDto(contactRepository.save(DomainGenerator.createContact(null, userId, suffix, false)));
        }

        public Optional<ContactResponse> getContactFromDb(long id, String userId) {
            return contactRepository.findByIdAndUserId(id, userId).map(this::toContactDto);
        }

        public ContactResponse toContactDto(Contact contact) {
            return new ContactResponse(contact.getId(), contact.getUserId(), contact.getFirstName(),
                    contact.getLastName(),
                    contact.getEmail(), contact.getRelationship(),
                    contact.getPhoneNumbers().stream().map(this::toPhoneNumberResponse).collect(Collectors.toList()),
                    contact.getAddresses().stream().map(this::toAddressResponse).collect(Collectors.toList()));
        }

        public PhoneNumberDetailsResponse toPhoneNumberResponse(PhoneNumber phoneNumber) {
            return new PhoneNumberDetailsResponse(phoneNumber.getId(), phoneNumber.getNumber(), phoneNumber.getDescription());
        }

        public AddressResponse toAddressResponse(Address address) {
            return new AddressResponse(address.getId(), address.getCountry(), address.getTown(), address.getStreet(),
                    address.getNumber(), address.getEntrance(), address.getFlatNumber());
        }
    }
}
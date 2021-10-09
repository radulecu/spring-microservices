package ro.rasel.spring.microservices.contactservice.controller.v1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactResponse;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.service.ContactService;
import ro.rasel.spring.microservices.contactservice.utils.DomainGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static ro.rasel.spring.microservices.contactservice.utils.DtoGenerator.createContactRequestBuilder;
import static ro.rasel.spring.microservices.contactservice.utils.DtoGenerator.createContactResponseBuilder;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.CONTACT_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.USER_ID;

@ExtendWith(MockitoExtension.class)
class ContactRestControllerTest {
    @InjectMocks
    ContactRestController contactRestController;

    @SuppressWarnings("unused")
    @Spy
    ModelMapper modelMapper;

    @Mock
    ContactService contactService;

    @Test
    void shouldGetContactCollectionWhenContactsExist() {
        final List<Contact> contacts = Collections.singletonList(DomainGenerator.createContact(true));
        final List<ContactResponse> contactResponses =
                Collections.singletonList(createContactResponseBuilder().build());
        when(contactService.getContacts(USER_ID)).thenReturn(contacts);

        final ResponseEntity<Collection<ContactResponse>> result = contactRestController.getContacts(USER_ID);

        assertThat(result.getBody(), is(contactResponses));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyContactsCollectionWhenContactsDoesNotExist() {
        when(contactService.getContacts(USER_ID)).thenReturn(Collections.emptyList());

        final ResponseEntity<Collection<ContactResponse>> result = contactRestController.getContacts(USER_ID);

        assertThat(result.getBody(), is((Collection<Contact>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetContactWhenContactExist() {
        final Contact contact = DomainGenerator.createContact(true);
        final ContactResponse contactResponse = createContactResponseBuilder().build();
        when(contactService.getContact(USER_ID, CONTACT_ID)).thenReturn(Optional.of(contact));

        final ResponseEntity<ContactResponse> result = contactRestController.getContact(USER_ID, CONTACT_ID);

        assertThat(result.getBody(), is(contactResponse));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoContactWhenContactsDoesNotExist() {
        final ResponseEntity<ContactResponse> result = contactRestController.getContact(USER_ID, CONTACT_ID);

        assertThat(result.getBody(), is((Contact) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateContact() {
        final ContactRequest contactDetails = createContactRequestBuilder().build();
        final Contact contactServiceRequest = DomainGenerator.createContact(null, USER_ID, "", false);
        final Contact contactServiceResponse = DomainGenerator.createContact(true);
        final ContactResponse contactResponse = createContactResponseBuilder().build();

        when(contactService.createContact(USER_ID, contactServiceRequest)).thenReturn(contactServiceResponse);
        final ContactResponse result = contactRestController.createContact(USER_ID, contactDetails);

        assertThat(result, is(contactResponse));
    }

    @Test
    void shouldUpdateContactWhenContactExists() {
        final ContactRequest contactDetails = createContactRequestBuilder().build();
        final Contact contactServiceRequest = DomainGenerator.createContact(false);
        final Contact contactServiceResponse = DomainGenerator.createContact(true);
        final ContactResponse contactResponse = createContactResponseBuilder().build();

        when(contactService.updateContact(contactServiceRequest)).thenReturn(Optional.of(contactServiceResponse));
        final ResponseEntity<ContactResponse> result =
                contactRestController.updateContact(USER_ID, CONTACT_ID, contactDetails);

        assertThat(result.getBody(), is(contactResponse));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateContactAndContactDoesNotExists() {
        final ContactRequest contactDetails = createContactRequestBuilder().build();
        final Contact contactRequest = DomainGenerator.createContact(false);

        when(contactService.updateContact(contactRequest)).thenReturn(Optional.empty());
        final ResponseEntity<ContactResponse> result =
                contactRestController.updateContact(USER_ID, CONTACT_ID, contactDetails);

        assertThat(result.getBody(), is((ContactResponse) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteContactWhenContactExists() {
        when(contactService.deleteContact(USER_ID, CONTACT_ID)).thenReturn(true);
        final ResponseEntity<Void> result = contactRestController.deleteContact(USER_ID, CONTACT_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteContactAndContactDoesNotExists() {
        when(contactService.deleteContact(USER_ID, CONTACT_ID)).thenReturn(false);
        final ResponseEntity<Void> result = contactRestController.deleteContact(USER_ID, CONTACT_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
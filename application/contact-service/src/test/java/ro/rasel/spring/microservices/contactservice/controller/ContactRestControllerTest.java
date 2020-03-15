package ro.rasel.spring.microservices.contactservice.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.contactservice.controller.dto.ContactDetailsDto;
import ro.rasel.spring.microservices.contactservice.controller.dto.ContactDto;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.service.ContactService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.CONTACT_ID;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.EMAIL;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.FIRST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.LAST_NAME;
import static ro.rasel.spring.microservices.contactservice.utils.TestConstants.RELATIONSHIP;
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
        final List<Contact> contacts =
                Collections.singletonList(new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP));
        final List<ContactDto> contactDtos =
                Collections
                        .singletonList(new ContactDto(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP));
        when(contactService.getContacts(USER_ID)).thenReturn(contacts);

        final ResponseEntity<Collection<ContactDto>> result = contactRestController.getContacts(USER_ID);

        assertThat(result.getBody(), is(contactDtos));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyContactsCollectionWhenContactsDoesNotExist() {
        when(contactService.getContacts(USER_ID)).thenReturn(Collections.emptyList());

        final ResponseEntity<Collection<ContactDto>> result = contactRestController.getContacts(USER_ID);

        assertThat(result.getBody(), is((Collection<Contact>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetContactWhenContactExist() {
        final Contact contact = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final ContactDto contactDto = new ContactDto(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        when(contactService.getContact(USER_ID, CONTACT_ID)).thenReturn(Optional.of(contact));

        final ResponseEntity<ContactDto> result = contactRestController.getContact(USER_ID, CONTACT_ID);

        assertThat(result.getBody(), is(contactDto));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoContactWhenContactsDoesNotExist() {
        final ResponseEntity<ContactDto> result = contactRestController.getContact(USER_ID, CONTACT_ID);

        assertThat(result.getBody(), is((Contact) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateContact() {
        final ContactDetailsDto contactDetails = new ContactDetailsDto(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contactRequest = new Contact(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contact = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final ContactDto contactDto = new ContactDto(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        when(contactService.createContact(USER_ID, contactRequest)).thenReturn(contact);
        final ContactDto result = contactRestController.createContact(USER_ID, contactDetails);

        assertThat(result, is(contactDto));
    }

    @Test
    void shouldUpdateContactWhenContactExists() {
        final ContactDetailsDto contactDetails = new ContactDetailsDto(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contact = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final ContactDto contactDto = new ContactDto(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        when(contactService.updateContact(contact)).thenReturn(Optional.of(contact));
        final ResponseEntity<ContactDto> result =
                contactRestController.updateContact(USER_ID, CONTACT_ID, contactDetails);

        assertThat(result.getBody(), is(contactDto));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateContactAndContactDoesNotExists() {
        final ContactDetailsDto contactDetails = new ContactDetailsDto(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contactRequest = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        when(contactService.updateContact(contactRequest)).thenReturn(Optional.empty());
        final ResponseEntity<ContactDto> result =
                contactRestController.updateContact(USER_ID, CONTACT_ID, contactDetails);

        assertThat(result.getBody(), is((ContactDto) null));
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
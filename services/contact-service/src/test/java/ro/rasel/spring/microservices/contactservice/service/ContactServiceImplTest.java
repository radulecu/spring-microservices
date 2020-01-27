package ro.rasel.spring.microservices.contactservice.service;

import com.google.common.collect.ImmutableList;
import org.hamcrest.*;
import org.hamcrest.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import ro.rasel.spring.microservices.contactservice.dao.ContactRepository;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.domain.ContactDetails;

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

@SuppressWarnings("OptionalGetWithoutIsPresent")
@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {
    @InjectMocks
    ContactServiceImpl contactService1;

    @Mock
    ContactRepository contactRepository;

    @Test
    void shouldGetContactCollectionWhenContactsExist() {
        final List<Contact> contacts =
                ImmutableList.of(new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP));
        when(contactRepository.findByUserId(USER_ID)).thenReturn(contacts);

        final List<Contact> result = contactService1.getContacts(USER_ID);

        assertThat(result, is(contacts));
    }

    @Test
    void shouldGetEmptyContactsCollectionWhenContactsDoesNotExist() {
        when(contactRepository.findByUserId(USER_ID)).thenReturn(Collections.emptyList());

        final List<Contact> result = contactService1.getContacts(USER_ID);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    void shouldGetContactWhenContactExist() {
        final Contact contact = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        when(contactRepository.findByIdAndUserId(CONTACT_ID, USER_ID)).thenReturn(Optional.of(contact));

        final Optional<Contact> result = contactService1.getContact(USER_ID, CONTACT_ID);

        assertThat(result.get(), is(contact));
    }

    @Test
    void shouldGetNoContactWhenContactsDoesNotExist() {
        final Optional<Contact> result = contactService1.getContact(USER_ID, CONTACT_ID);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void shouldCreateContact() {
        final ContactDetails contactDetails = new ContactDetails(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contact = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        when(contactRepository.save(new Contact(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP)))
                .thenReturn(contact);
        final Contact result = contactService1.createContact(USER_ID, contactDetails);

        assertThat(result, is(contact));
    }

    @Test
    void shouldUpdateContactWhenContactExists() {
        final ContactDetails contactDetails = new ContactDetails(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);
        final Contact contactSpy = Mockito.spy(Contact.class);
        final Contact contact = new Contact(CONTACT_ID, USER_ID, FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        when(contactRepository.findByIdAndUserId(CONTACT_ID, USER_ID)).thenReturn(Optional.of(contactSpy));
        when(contactRepository.save(contactSpy)).thenReturn(contact);
        final Optional<Contact> result = contactService1.updateContact(USER_ID, CONTACT_ID, contactDetails);

        assertThat(result.get(), is(contact));

        MatcherAssert.assertThat(contactSpy.getFirstName(), Is.is(FIRST_NAME));
        MatcherAssert.assertThat(contactSpy.getLastName(), Is.is(LAST_NAME));
        MatcherAssert.assertThat(contactSpy.getEmail(), Is.is(EMAIL));
        MatcherAssert.assertThat(contactSpy.getRelationship(), Is.is(RELATIONSHIP));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateContactAndContactDoesNotExists() {
        final ContactDetails contactDetails = new ContactDetails(FIRST_NAME, LAST_NAME, EMAIL, RELATIONSHIP);

        when(contactRepository.findByIdAndUserId(CONTACT_ID, USER_ID)).thenReturn(Optional.empty());
        final Optional<Contact> result = contactService1.updateContact(USER_ID, CONTACT_ID, contactDetails);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void shouldDeleteContactWhenContactExists() {
        final Contact contactSpy = Mockito.spy(Contact.class);
        when(contactRepository.findByIdAndUserId(CONTACT_ID, USER_ID)).thenReturn(Optional.of(contactSpy));

        final boolean result = contactService1.deleteContact(USER_ID, CONTACT_ID);

        assertThat(result, is(true));

        verify(contactRepository, Mockito.times(1)).delete(contactSpy);
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteContactAndContactDoesNotExists() {
        final boolean result = contactService1.deleteContact(USER_ID, CONTACT_ID);

        assertThat(result, is(false));

        verify(contactRepository, Mockito.times(0)).delete(any());
    }
}
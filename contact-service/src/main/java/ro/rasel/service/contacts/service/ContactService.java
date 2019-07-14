package ro.rasel.service.contacts.service;

import ro.rasel.service.contacts.domain.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> getContacts(String userId);

    Optional<Contact> getContact(String userId, Long contactId);

    Contact createContact(Contact contact);

    Optional<Contact> updateContact(Contact contact);

    boolean deleteContact(String userId, Long contactId);
}

package ro.rasel.service.contacts.service;

import ro.rasel.service.contacts.domain.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getContacts(String userId);

    Contact getContact(String userId, Long contactId);

    Contact createContact(Contact contact);

    boolean deleteContact(String userId, Long contactId);
}

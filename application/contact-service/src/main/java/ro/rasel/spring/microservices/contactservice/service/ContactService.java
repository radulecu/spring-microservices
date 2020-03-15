package ro.rasel.spring.microservices.contactservice.service;

import ro.rasel.spring.microservices.contactservice.domain.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> getContacts(String userId);

    Optional<Contact> getContact(String userId, long contactId);

    Contact createContact(String userId, Contact contactDetails);

    Optional<Contact> updateContact(Contact contactDetails);

    boolean deleteContact(String userId, long contactId);
}

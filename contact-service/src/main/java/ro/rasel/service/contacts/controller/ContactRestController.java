package ro.rasel.service.contacts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.contacts.domain.Contact;
import ro.rasel.service.contacts.service.ContactService;

import java.util.Collection;
import java.util.List;

@RestController
public class ContactRestController implements ContactApi {

    private final ContactService contactService;

    public ContactRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ResponseEntity<Collection<Contact>> getContacts(@PathVariable String userId) {
        final List<Contact> contacts = contactService.getContacts(userId);
        return contacts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(contacts);
    }

    @Override
    public ResponseEntity<Contact> getContact(@PathVariable String userId, @PathVariable long contactId) {
        final Contact contact = contactService.getContact(userId, contactId);
        return contact == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(contact);
    }

    @Override
    public Contact createContact(@PathVariable String userId, @RequestBody Contact contact) {
        final Contact contactInstance = new Contact(userId, contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getRelationship());
        return this.contactService.createContact(contactInstance);
    }

    @Override
    public ResponseEntity<Void> deleteContact(@PathVariable String userId, @RequestBody long contactId) {
        return contactService.deleteContact(userId, contactId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}

package ro.rasel.spring.microserivces.contactservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microserivces.contactservice.domain.Contact;
import ro.rasel.spring.microserivces.contactservice.domain.ContactDetails;
import ro.rasel.spring.microserivces.contactservice.service.ContactService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        final Optional<Contact> contact = contactService.getContact(userId, contactId);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Contact createContact(@PathVariable String userId, @RequestBody ContactDetails contactDetails) {
        final Contact contactInstance =
                new Contact(userId, contactDetails.getFirstName(), contactDetails.getLastName(),
                        contactDetails.getEmail(), contactDetails.getRelationship());
        return contactService.createContact(contactInstance);
    }

    @Override
    public ResponseEntity<Contact> updateContact(
            @PathVariable String userId, @PathVariable long contactId, @RequestBody ContactDetails contactDetails) {
        Contact contact =
                new Contact(contactId, userId, contactDetails.getFirstName(), contactDetails.getLastName(),
                        contactDetails.getEmail(), contactDetails.getRelationship());
        return contactService.updateContact(contact).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteContact(@PathVariable String userId, @PathVariable long contactId) {
        return contactService.deleteContact(userId, contactId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}

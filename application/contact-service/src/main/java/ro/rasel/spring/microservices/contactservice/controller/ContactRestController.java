package ro.rasel.spring.microservices.contactservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.domain.ContactDetails;
import ro.rasel.spring.microservices.contactservice.service.ContactService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class ContactRestController implements ContactApi {
    private static final Logger LOG = LoggerFactory.getLogger(ContactRestController.class);

    private final ContactService contactService;

    public ContactRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ResponseEntity<Collection<Contact>> getContacts(@PathVariable String userId) {
        LOG.debug("getting contacts for userId={}", userId);
        final List<Contact> contacts = contactService.getContacts(userId);
        return contacts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(contacts);
    }

    @Override
    public ResponseEntity<Contact> getContact(@PathVariable String userId, @PathVariable long contactId) {
        LOG.debug("getting contact for userId={} and contactId={}", userId, contactId);
        final Optional<Contact> contact = contactService.getContact(userId, contactId);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Contact createContact(@PathVariable String userId, @RequestBody ContactDetails contactDetails) {
        LOG.debug("creating contact for userId={}", userId);
        return contactService.createContact(userId, contactDetails);
    }

    @Override
    public ResponseEntity<Contact> updateContact(
            @PathVariable String userId, @PathVariable long contactId, @RequestBody ContactDetails contactDetails) {
        LOG.debug("updating contact for userId={} and contactId={}", userId, contactId);
        return contactService.updateContact(userId, contactId, contactDetails).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteContact(@PathVariable String userId, @PathVariable long contactId) {
        LOG.debug("deleting contact for userId={} and contactId={}", userId, contactId);
        return contactService.deleteContact(userId, contactId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}

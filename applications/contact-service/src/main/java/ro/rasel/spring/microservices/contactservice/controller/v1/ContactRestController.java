package ro.rasel.spring.microservices.contactservice.controller.v1;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactResponse;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.service.ContactService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ContactRestController implements ContactApi {
    private static final Logger LOG = LoggerFactory.getLogger(ContactRestController.class);

    private final ContactService contactService;
    private final ModelMapper modelMapper;

    public ContactRestController(ContactService contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Collection<ContactResponse>> getContacts(@PathVariable String userId) {
        LOG.debug("getting contacts for userId={}", userId);
        final List<Contact> contacts = contactService.getContacts(userId);
        return contacts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(toContactResponses(contacts));
    }

    @Override
    public ResponseEntity<ContactResponse> getContact(@PathVariable String userId, @PathVariable long contactId) {
        LOG.debug("getting contact for userId={} and contactId={}", userId, contactId);
        final Optional<Contact> contact = contactService.getContact(userId, contactId);
        return contact
                .map(this::toContactDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ContactResponse createContact(@PathVariable String userId, @RequestBody ContactRequest contactRequest) {
        LOG.debug("creating contact for userId={}", userId);
        final Contact contact = toContact(userId, null, contactRequest);

        return toContactDto(contactService.createContact(userId, contact));
    }

    @Override
    public ResponseEntity<ContactResponse> updateContact(
            @PathVariable String userId, @PathVariable long contactId,
            @RequestBody ContactRequest contactRequest) {
        LOG.debug("updating contact for userId={} and contactId={}", userId, contactId);

        Contact contact = toContact(userId, contactId, contactRequest);

        return contactService
                .updateContact(contact)
                .map(this::toContactDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteContact(@PathVariable String userId, @PathVariable long contactId) {
        LOG.debug("deleting contact for userId={} and contactId={}", userId, contactId);
        return contactService.deleteContact(userId, contactId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    private Contact toContact(String userId, Long contactId, ContactRequest contactRequest) {
        final Contact contact = modelMapper.map(contactRequest, Contact.class);
        contact.setId(contactId);
        contact.setUserId(userId);
        if (contact.getPhoneNumbers()==null){
            contact.setPhoneNumbers(Collections.emptyList());
        }
        if (contact.getAddresses()==null){
            contact.setAddresses(Collections.emptyList());
        }
        return contact;
    }

    private List<ContactResponse> toContactResponses(List<Contact> contacts) {
        return contacts.stream().map(this::toContactDto)
                .collect(Collectors.toList());
    }

    private ContactResponse toContactDto(Contact contact) {
        return modelMapper.map(contact, ContactResponse.class);
    }
}

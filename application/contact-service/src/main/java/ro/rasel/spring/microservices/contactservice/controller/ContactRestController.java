package ro.rasel.spring.microservices.contactservice.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.contactservice.controller.dto.ContactDetailsDto;
import ro.rasel.spring.microservices.contactservice.controller.dto.ContactDto;
import ro.rasel.spring.microservices.contactservice.domain.Contact;
import ro.rasel.spring.microservices.contactservice.service.ContactService;

import java.util.Collection;
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
    public ResponseEntity<Collection<ContactDto>> getContacts(@PathVariable String userId) {
        LOG.debug("getting contacts for userId={}", userId);
        final List<Contact> contacts = contactService.getContacts(userId);
        return contacts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(toContactDtos(contacts));
    }

    @Override
    public ResponseEntity<ContactDto> getContact(@PathVariable String userId, @PathVariable long contactId) {
        LOG.debug("getting contact for userId={} and contactId={}", userId, contactId);
        final Optional<Contact> contact = contactService.getContact(userId, contactId);
        return contact
                .map(this::toContactDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ContactDto createContact(@PathVariable String userId, @RequestBody ContactDetailsDto contactDetails) {
        LOG.debug("creating contact for userId={}", userId);
        final Contact contact = new Contact(userId, contactDetails.getFirstName(), contactDetails.getLastName(),
                contactDetails.getEmail(), contactDetails.getRelationship());

        return toContactDto(contactService.createContact(userId, contact));
    }

    @Override
    public ResponseEntity<ContactDto> updateContact(
            @PathVariable String userId, @PathVariable long contactId, @RequestBody ContactDetailsDto contactDetails) {
        LOG.debug("updating contact for userId={} and contactId={}", userId, contactId);

        Contact contact =
                toContact(userId, contactId, contactDetails);

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

    private Contact toContact(String userId, long contactId, ContactDetailsDto contactDetails) {
        return new Contact(contactId, userId, contactDetails.getFirstName(), contactDetails.getLastName(),
                contactDetails.getEmail(), contactDetails.getRelationship());
    }

    private List<ContactDto> toContactDtos(List<Contact> contacts) {
        return contacts.stream().map(this::toContactDto)
                .collect(Collectors.toList());
    }

    private ContactDto toContactDto(Contact contact) {
        return modelMapper.map(contact, ContactDto.class);
    }
}

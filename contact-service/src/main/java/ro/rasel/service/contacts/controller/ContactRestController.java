package ro.rasel.service.contacts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.contacts.dao.ContactRepository;
import ro.rasel.service.contacts.domain.Contact;

import java.util.Collection;

@RestController
@RequestMapping("/contacts/{userId}")
public class ContactRestController {

    private final ContactRepository contactRepository;

    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public Collection<Contact> getContacts(@PathVariable String userId) {
        return this.contactRepository.findByUserId(userId);
    }

    @GetMapping(value = "/{contactId}")
    public Contact getContact(@PathVariable String userId, @PathVariable Long contactId) {
        return this.contactRepository.findByUserIdAndId(userId, contactId);
    }

    @PostMapping
    public Contact createContact(@PathVariable String userId,
                                 @RequestBody Contact contact) {
        return this.contactRepository.save(new Contact(userId, contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getRelationship()));
    }

}

package ro.rasel.service.contacts.controller;

import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Contact> getContacts(@PathVariable String userId) {
        return this.contactRepository.findByUserId(userId);
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.GET)
    public Contact getContact(@PathVariable String userId, @PathVariable Long contactId) {
        return this.contactRepository.findByUserIdAndId(userId, contactId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Contact createContact(@PathVariable String userId,
                                 @RequestBody Contact contact) {
        return this.contactRepository.save(new Contact(userId, contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getRelationship()));
    }

}

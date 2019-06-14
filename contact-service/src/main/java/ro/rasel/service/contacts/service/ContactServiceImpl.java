package ro.rasel.service.contacts.service;

import org.springframework.stereotype.Service;
import ro.rasel.service.contacts.dao.ContactRepository;
import ro.rasel.service.contacts.domain.Contact;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getContacts(String userId) {
        return contactRepository.findByUserId(userId);
    }

    @Override
    public Contact getContact(String userId, Long contactId) {
        return contactRepository.findByIdAndUserId(contactId, userId);
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public boolean deleteContact(String userId, Long contactId) {
        return contactRepository.deleteByIdAndUserId(contactId, userId) >=1;
    }
}

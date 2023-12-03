package ro.rasel.service.contacts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.service.contacts.domain.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByUserId(String userId);

    Contact findByUserIdAndId(String userId, Long id);

}

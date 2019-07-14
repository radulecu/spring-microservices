package ro.rasel.service.contacts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.service.contacts.domain.Contact;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByUserId(String userId);

    Optional<Contact> findByIdAndUserId(long id, String userId);
}

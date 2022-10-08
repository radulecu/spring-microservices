package ro.rasel.spring.microservices.bookmarkservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserId(String userId);

    Optional<Bookmark> findByIdAndUserId(long id, String userId);

    List<Bookmark> findByUserIdAndLabel(String userId, String label);
}

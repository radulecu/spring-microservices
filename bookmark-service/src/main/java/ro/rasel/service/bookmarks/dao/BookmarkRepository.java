package ro.rasel.service.bookmarks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserId(String userId);

    Bookmark findByIdAndUserId(long id, String userId);

    int deleteByIdAndUserId(long id, String userId);
}

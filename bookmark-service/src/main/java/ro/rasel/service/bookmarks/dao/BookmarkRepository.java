package ro.rasel.service.bookmarks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserId(String userId);

    Bookmark findByUserIdAndId(String userId, Long id);

}

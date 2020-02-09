package ro.rasel.spring.microservices.bookmarkservice.service;

import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookmarkService {
    List<Bookmark> getBookmarks(String userId);

    Optional<Bookmark> getBookmark(String userId, long bookmarkId);

    Bookmark createBookmark(Bookmark bookmark);

    Optional<Bookmark> updateBookmark(Bookmark bookmark);

    boolean deleteBookmark(String userId, long bookmarkId);
}

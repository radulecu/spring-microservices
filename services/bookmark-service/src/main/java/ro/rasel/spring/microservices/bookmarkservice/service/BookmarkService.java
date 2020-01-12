package ro.rasel.spring.microservices.bookmarkservice.service;

import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microservices.bookmarkservice.domain.BookmarkDetails;

import java.util.List;
import java.util.Optional;

public interface BookmarkService {
    List<Bookmark> getBookmarks(String userId);

    Optional<Bookmark> getBookmark(String userId, long bookmarkId);

    Bookmark createBookmark(String userId, BookmarkDetails bookmarkDetails);

    Optional<Bookmark> updateBookmark(String userId, long bookmarkId, BookmarkDetails bookmarkDetails);

    boolean deleteBookmark(String userId, long bookmarkId);
}

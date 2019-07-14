package ro.rasel.service.bookmarks.service;

import ro.rasel.service.bookmarks.domain.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookmarkService {
    List<Bookmark> getBookmarks(String userId);

    Optional<Bookmark> getBookmark(String userId, Long bookmarkId);

    Bookmark createBookmark(Bookmark bookmark);

    Optional<Bookmark> updateBookmark(Bookmark bookmark);

    boolean deleteBookmark(long bookmarkId, String userId);
}

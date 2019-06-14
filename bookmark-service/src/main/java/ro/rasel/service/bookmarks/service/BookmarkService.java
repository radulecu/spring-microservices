package ro.rasel.service.bookmarks.service;

import ro.rasel.service.bookmarks.domain.Bookmark;

import java.util.List;

public interface BookmarkService {
    List<Bookmark> getBookmarks(String userId);

    Bookmark getBookmark(String userId, Long bookmarkId);

    Bookmark createBookmark(Bookmark bookmark);

    boolean deleteBookmark(String userId, Long bookmarkId);
}

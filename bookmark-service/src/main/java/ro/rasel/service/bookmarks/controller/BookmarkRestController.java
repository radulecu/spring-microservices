package ro.rasel.service.bookmarks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.bookmarks.domain.Bookmark;
import ro.rasel.service.bookmarks.service.BookmarkService;

import java.util.Collection;
import java.util.List;

@RestController
public class BookmarkRestController implements BookmarkApi {

    private final BookmarkService bookmarkService;

    public BookmarkRestController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @Override
    public ResponseEntity<Collection<Bookmark>> getBookmarks(@PathVariable String userId) {
        final List<Bookmark> bookmarks = bookmarkService.getBookmarks(userId);
        return bookmarks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookmarks);
    }

    @Override
    public ResponseEntity<Bookmark> getBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        final Bookmark bookmark = this.bookmarkService.getBookmark(userId, bookmarkId);
        return bookmark == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookmark);
    }

    @Override
    public Bookmark createBookmark(@PathVariable String userId, @RequestBody Bookmark bookmark) {
        Bookmark bookmarkInstance = new Bookmark(userId, bookmark.getHref(),
                bookmark.getDescription(), bookmark.getLabel());
        return bookmarkService.createBookmark(bookmarkInstance);
    }

    @Override
    public ResponseEntity<Void> deleteBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        return bookmarkService.deleteBookmark(userId, bookmarkId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}

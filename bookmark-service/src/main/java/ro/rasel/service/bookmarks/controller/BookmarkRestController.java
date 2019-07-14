package ro.rasel.service.bookmarks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.bookmarks.domain.Bookmark;
import ro.rasel.service.bookmarks.domain.BookmarkDetails;
import ro.rasel.service.bookmarks.service.BookmarkService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        final Optional<Bookmark> bookmark = this.bookmarkService.getBookmark(userId, bookmarkId);
        return bookmark.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Bookmark createBookmark(@PathVariable String userId, @RequestBody BookmarkDetails bookmarkDetails) {
        Bookmark bookmark =
                new Bookmark(userId, bookmarkDetails.getHref(), bookmarkDetails.getDescription(),
                        bookmarkDetails.getLabel());
        return bookmarkService.createBookmark(bookmark);
    }

    @Override
    public ResponseEntity<Bookmark> updateBookmark(
            @PathVariable String userId, @PathVariable long bookmarkId, @RequestBody BookmarkDetails bookmarkDetails) {
        Bookmark bookmark =
                new Bookmark(bookmarkId, userId, bookmarkDetails.getHref(), bookmarkDetails.getDescription(),
                        bookmarkDetails.getLabel());
        return bookmarkService.updateBookmark(bookmark).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        return bookmarkService.deleteBookmark(bookmarkId, userId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}

package ro.rasel.spring.microservices.bookmarkservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microservices.bookmarkservice.domain.BookmarkDetails;
import ro.rasel.spring.microservices.bookmarkservice.service.BookmarkService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class BookmarkRestController implements BookmarkApi {
    private static final Logger LOG = LoggerFactory.getLogger(BookmarkRestController.class);

    private final BookmarkService bookmarkService;

    public BookmarkRestController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @Override
    public ResponseEntity<Collection<Bookmark>> getBookmarks(@PathVariable String userId) {
        LOG.debug("getting bookmarks for userId={}", userId);
        final List<Bookmark> bookmarks = bookmarkService.getBookmarks(userId);
        return bookmarks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookmarks);
    }

    @Override
    public ResponseEntity<Bookmark> getBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        LOG.debug("getting bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        final Optional<Bookmark> bookmark = this.bookmarkService.getBookmark(userId, bookmarkId);
        return bookmark.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Bookmark createBookmark(@PathVariable String userId, @RequestBody BookmarkDetails bookmarkDetails) {
        LOG.debug("creating bookmark for userId={}", userId);
        return bookmarkService.createBookmark(userId, bookmarkDetails);
    }

    @Override
    public ResponseEntity<Bookmark> updateBookmark(
            @PathVariable String userId, @PathVariable long bookmarkId, @RequestBody BookmarkDetails bookmarkDetails) {
        LOG.debug("updating bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        return bookmarkService.updateBookmark(userId, bookmarkId, bookmarkDetails).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        LOG.debug("deleting bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        return bookmarkService.deleteBookmark(userId, bookmarkId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}

package ro.rasel.spring.microservices.bookmarkservice.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkRequest;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkResponse;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microservices.bookmarkservice.service.BookmarkService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookmarkRestController implements BookmarkApi {
    private static final Logger LOG = LoggerFactory.getLogger(BookmarkRestController.class);

    private final BookmarkService bookmarkService;

    public BookmarkRestController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @Override
    public ResponseEntity<Collection<BookmarkResponse>> getBookmarks(@PathVariable String userId) {
        LOG.debug("getting bookmarks for userId={}", userId);
        final List<BookmarkResponse> bookmarks =
                bookmarkService.getBookmarks(userId).stream()
                        .map(BookmarkResponse::new)
                        .collect(Collectors.toList());
        return bookmarks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookmarks);
    }

    @Override
    public ResponseEntity<BookmarkResponse> getBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        LOG.debug("getting bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        final Optional<BookmarkResponse> bookmark =
                this.bookmarkService.getBookmark(userId, bookmarkId).map(BookmarkResponse::new);
        return bookmark.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public BookmarkResponse createBookmark(@PathVariable String userId, @RequestBody BookmarkRequest bookmarkRequest) {
        LOG.debug("creating bookmark for userId={}", userId);
        final Bookmark bookmark = bookmarkRequest.getBookmark();
        bookmark.setUserId(userId);
        final Bookmark createdBookmark = bookmarkService.createBookmark(bookmark);
        return createdBookmark != null ? new BookmarkResponse(createdBookmark) : null;
    }

    @Override
    public ResponseEntity<BookmarkResponse> updateBookmark(
            @PathVariable String userId, @PathVariable long bookmarkId, @RequestBody
            BookmarkRequest bookmarkRequest) {
        LOG.debug("updating bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        final Bookmark bookmark = bookmarkRequest.getBookmark();
        bookmark.setId(bookmarkId);
        bookmark.setUserId(userId);
        return bookmarkService.updateBookmark(bookmark)
                .map(BookmarkResponse::new)
                .map(ResponseEntity::ok)
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

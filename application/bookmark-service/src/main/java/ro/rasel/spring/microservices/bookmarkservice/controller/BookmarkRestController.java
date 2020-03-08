package ro.rasel.spring.microservices.bookmarkservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.bookmarkservice.controller.dto.BookmarkDetailsDto;
import ro.rasel.spring.microservices.bookmarkservice.controller.dto.BookmarkDto;
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
    public ResponseEntity<Collection<BookmarkDto>> getBookmarks(@PathVariable String userId) {
        LOG.debug("getting bookmarks for userId={}", userId);
        final List<BookmarkDto> bookmarks =
                bookmarkService.getBookmarks(userId).stream()
                        .map(BookmarkDto::new)
                        .collect(Collectors.toList());
        return bookmarks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookmarks);
    }

    @Override
    public ResponseEntity<BookmarkDto> getBookmark(@PathVariable String userId, @PathVariable long bookmarkId) {
        LOG.debug("getting bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        final Optional<BookmarkDto> bookmark =
                this.bookmarkService.getBookmark(userId, bookmarkId).map(BookmarkDto::new);
        return bookmark.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public BookmarkDto createBookmark(@PathVariable String userId, @RequestBody BookmarkDetailsDto bookmarkDetailsDto) {
        LOG.debug("creating bookmark for userId={}", userId);
        final Bookmark bookmark = bookmarkDetailsDto.getBookmark();
        bookmark.setUserId(userId);
        final Bookmark createdBookmark = bookmarkService.createBookmark(bookmark);
        return createdBookmark != null ? new BookmarkDto(createdBookmark) : null;
    }

    @Override
    public ResponseEntity<BookmarkDto> updateBookmark(
            @PathVariable String userId, @PathVariable long bookmarkId, @RequestBody
            BookmarkDetailsDto bookmarkDetailsDto) {
        LOG.debug("updating bookmark for userId={} and bookmarkId={}", userId, bookmarkId);
        final Bookmark bookmark = bookmarkDetailsDto.getBookmark();
        bookmark.setId(bookmarkId);
        bookmark.setUserId(userId);
        return bookmarkService.updateBookmark(bookmark)
                .map(BookmarkDto::new)
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

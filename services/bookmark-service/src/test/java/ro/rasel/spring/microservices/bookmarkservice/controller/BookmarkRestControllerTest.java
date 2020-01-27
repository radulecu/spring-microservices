package ro.rasel.spring.microservices.bookmarkservice.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microservices.bookmarkservice.domain.BookmarkDetails;
import ro.rasel.spring.microservices.bookmarkservice.service.BookmarkService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.BOOKMARK_ID;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.DESCRIPTION;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.HREF;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.LABEL;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.USER_ID;

@ExtendWith(MockitoExtension.class)
class BookmarkRestControllerTest {
    @InjectMocks
    BookmarkRestController bookmarkRestController;

    @Mock
    BookmarkService bookmarkService;

    @Test
    void shouldGetBookmarkCollectionWhenBookmarksExist() {
        final List<Bookmark> bookmarks =
                Collections.singletonList(new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL));
        when(bookmarkService.getBookmarks(USER_ID)).thenReturn(bookmarks);

        final ResponseEntity<Collection<Bookmark>> result = bookmarkRestController.getBookmarks(USER_ID);

        assertThat(result.getBody(), is(bookmarks));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyBookmarksCollectionWhenBookmarksDoesNotExist() {
        when(bookmarkService.getBookmarks(USER_ID)).thenReturn(Collections.emptyList());

        final ResponseEntity<Collection<Bookmark>> result = bookmarkRestController.getBookmarks(USER_ID);

        assertThat(result.getBody(), is((Collection<Bookmark>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetBookmarkWhenBookmarkExist() {
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);
        when(bookmarkService.getBookmark(USER_ID, BOOKMARK_ID)).thenReturn(Optional.of(bookmark));

        final ResponseEntity<Bookmark> result = bookmarkRestController.getBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoBookmarkWhenBookmarksDoesNotExist() {
        final ResponseEntity<Bookmark> result = bookmarkRestController.getBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.getBody(), is((Bookmark) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateBookmark() {
        final BookmarkDetails
                bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkService.createBookmark(USER_ID, bookmarkDetails)).thenReturn(bookmark);
        final Bookmark result = bookmarkRestController.createBookmark(USER_ID, bookmarkDetails);

        assertThat(result, is(bookmark));
    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkService.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetails)).thenReturn(Optional.of(bookmark));
        final ResponseEntity<Bookmark> result =
                bookmarkRestController.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetails);

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);

        when(bookmarkService.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetails)).thenReturn(Optional.empty());
        final ResponseEntity<Bookmark> result =
                bookmarkRestController.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetails);

        assertThat(result.getBody(), is((Bookmark) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteBookmarkWhenBookmarkExists() {
        when(bookmarkService.deleteBookmark(USER_ID, BOOKMARK_ID)).thenReturn(true);
        final ResponseEntity<Void> result = bookmarkRestController.deleteBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteBookmarkAndBookmarkDoesNotExists() {
        when(bookmarkService.deleteBookmark(USER_ID, BOOKMARK_ID)).thenReturn(false);
        final ResponseEntity<Void> result = bookmarkRestController.deleteBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
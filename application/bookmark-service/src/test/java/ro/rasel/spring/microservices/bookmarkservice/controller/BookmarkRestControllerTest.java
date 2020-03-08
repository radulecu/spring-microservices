package ro.rasel.spring.microservices.bookmarkservice.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.bookmarkservice.controller.dto.BookmarkDetailsDto;
import ro.rasel.spring.microservices.bookmarkservice.controller.dto.BookmarkDto;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;
import ro.rasel.spring.microservices.bookmarkservice.service.BookmarkService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
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
    void shouldGetBookmarksWhenBookmarksExist() {
        final List<Bookmark> bookmarks =
                Collections.singletonList(new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL));
        when(bookmarkService.getBookmarks(USER_ID)).thenReturn(bookmarks);

        final ResponseEntity<Collection<BookmarkDto>> result = bookmarkRestController.getBookmarks(USER_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

        assertThat(result.getBody(), containsInAnyOrder(bookmarks.stream().map(BookmarkDto::new).toArray()));
    }

    @Test
    void shouldGetEmptyBookmarksCollectionWhenBookmarksDoesNotExist() {
        when(bookmarkService.getBookmarks(USER_ID)).thenReturn(Collections.emptyList());

        final ResponseEntity<Collection<BookmarkDto>> result = bookmarkRestController.getBookmarks(USER_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is((Collection<Bookmark>) null));
    }

    @Test
    void shouldGetBookmarkWhenBookmarkExist() {
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);
        when(bookmarkService.getBookmark(USER_ID, BOOKMARK_ID)).thenReturn(Optional.of(bookmark));

        final ResponseEntity<BookmarkDto> result = bookmarkRestController.getBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), is(new BookmarkDto(bookmark)));
    }

    @Test
    void shouldGetNoBookmarkWhenBookmarksDoesNotExist() {
        final ResponseEntity<BookmarkDto> result = bookmarkRestController.getBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is((Bookmark) null));
    }

    @Test
    void shouldCreateBookmark() {
        final Bookmark bookmark = new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL);
        final BookmarkDetailsDto
                bookmarkDetailsDto = new BookmarkDetailsDto(HREF, DESCRIPTION, LABEL);
        final Bookmark createdBookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkService.createBookmark(bookmark)).thenReturn(createdBookmark);
        final BookmarkDto result = bookmarkRestController.createBookmark(USER_ID, bookmarkDetailsDto);

        assertThat(result, is(new BookmarkDto(createdBookmark)));
    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);
        final BookmarkDetailsDto bookmarkDetailsDto = new BookmarkDetailsDto(HREF, DESCRIPTION, LABEL);

        when(bookmarkService.updateBookmark(bookmark)).thenReturn(Optional.of(bookmark));
        final ResponseEntity<BookmarkDto> result =
                bookmarkRestController.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetailsDto);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), is(new BookmarkDto(bookmark)));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);
        final BookmarkDetailsDto bookmarkDetailsDto = new BookmarkDetailsDto(HREF, DESCRIPTION, LABEL);

        when(bookmarkService.updateBookmark(bookmark)).thenReturn(Optional.empty());
        final ResponseEntity<BookmarkDto> result =
                bookmarkRestController.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetailsDto);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is((BookmarkDto) null));
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
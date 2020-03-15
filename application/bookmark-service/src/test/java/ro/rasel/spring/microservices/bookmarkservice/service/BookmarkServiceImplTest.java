package ro.rasel.spring.microservices.bookmarkservice.service;

import com.google.common.collect.ImmutableList;
import org.hamcrest.*;
import org.hamcrest.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import ro.rasel.spring.microservices.bookmarkservice.dao.BookmarkRepository;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

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

@SuppressWarnings("OptionalGetWithoutIsPresent")
@ExtendWith(MockitoExtension.class)
class BookmarkServiceImplTest {
    @InjectMocks
    BookmarkServiceImpl bookmarkService1;

    @Mock
    BookmarkRepository bookmarkRepository;

    @Test
    void shouldGetBookmarkCollectionWhenBookmarksExist() {
        final List<Bookmark> bookmarks = ImmutableList.of(new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL));
        when(bookmarkRepository.findByUserId(USER_ID)).thenReturn(bookmarks);

        final List<Bookmark> result = bookmarkService1.getBookmarks(USER_ID);

        assertThat(result, is(bookmarks));
    }

    @Test
    void shouldGetEmptyBookmarksCollectionWhenBookmarksDoesNotExist() {
        when(bookmarkRepository.findByUserId(USER_ID)).thenReturn(Collections.emptyList());

        final List<Bookmark> result = bookmarkService1.getBookmarks(USER_ID);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    void shouldGetBookmarkWhenBookmarkExist() {
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);
        when(bookmarkRepository.findByIdAndUserId(BOOKMARK_ID, USER_ID)).thenReturn(Optional.of(bookmark));

        final Optional<Bookmark> result = bookmarkService1.getBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.get(), is(bookmark));
    }

    @Test
    void shouldGetNoBookmarkWhenBookmarksDoesNotExist() {
        final Optional<Bookmark> result = bookmarkService1.getBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void shouldCreateBookmark() {
        final Bookmark bookmark = new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL);
        final Bookmark createdBookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkRepository.save(bookmark)).thenReturn(createdBookmark);
        final Bookmark result = bookmarkService1.createBookmark(bookmark);

        assertThat(result, is(createdBookmark));
    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final Bookmark bookmarkFromDb = new Bookmark(BOOKMARK_ID, USER_ID, HREF + 0, DESCRIPTION + 0, LABEL + 0);
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);
        final Bookmark updatedBookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkRepository.findByIdAndUserId(BOOKMARK_ID, USER_ID)).thenReturn(Optional.of(bookmarkFromDb));
        when(bookmarkRepository.save(bookmarkFromDb)).thenReturn(bookmark);
        final Optional<Bookmark> result = bookmarkService1.updateBookmark(updatedBookmark);

        assertThat(result.get(), is(bookmark));

        MatcherAssert.assertThat(bookmarkFromDb.getHref(), Is.is(HREF));
        MatcherAssert.assertThat(bookmarkFromDb.getDescription(), Is.is(DESCRIPTION));
        MatcherAssert.assertThat(bookmarkFromDb.getLabel(), Is.is(LABEL));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkRepository.findByIdAndUserId(BOOKMARK_ID, USER_ID)).thenReturn(Optional.empty());
        final Optional<Bookmark> result = bookmarkService1.updateBookmark(bookmark);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void shouldDeleteBookmarkWhenBookmarkExists() {
        final Bookmark bookmarkSpy = Mockito.mock(Bookmark.class);
        when(bookmarkRepository.findByIdAndUserId(BOOKMARK_ID, USER_ID)).thenReturn(Optional.of(bookmarkSpy));

        final boolean result = bookmarkService1.deleteBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result, is(true));

        verify(bookmarkRepository, Mockito.times(1)).delete(bookmarkSpy);
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteBookmarkAndBookmarkDoesNotExists() {
        final boolean result = bookmarkService1.deleteBookmark(USER_ID, BOOKMARK_ID);

        assertThat(result, is(false));

        verify(bookmarkRepository, Mockito.times(0)).delete(any());
    }
}
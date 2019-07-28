package ro.rasel.service.bookmarks.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import ro.rasel.service.bookmarks.dao.BookmarkRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;
import ro.rasel.service.bookmarks.domain.BookmarkDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static ro.rasel.service.bookmarks.utils.TestConstants.BOOKMARK_ID;
import static ro.rasel.service.bookmarks.utils.TestConstants.DESCRIPTION;
import static ro.rasel.service.bookmarks.utils.TestConstants.HREF;
import static ro.rasel.service.bookmarks.utils.TestConstants.LABEL;
import static ro.rasel.service.bookmarks.utils.TestConstants.USER_ID;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@ExtendWith(MockitoExtension.class)
class BookmarkServiceImplTest {
    @InjectMocks
    BookmarkServiceImpl bookmarkService1;

    @Mock
    BookmarkRepository bookmarkRepository;

    @Test
    void shouldGetBookmarkCollectionWhenBookmarksExist() {
        final List<Bookmark> bookmarks =
                Collections.singletonList(new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL));
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
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkRepository.save(new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL))).thenReturn(bookmark);
        final Bookmark result = bookmarkService1.createBookmark(USER_ID, bookmarkDetails);

        assertThat(result, is(bookmark));
    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);
        final Bookmark bookmarkSpy = Mockito.spy(Bookmark.class);
        final Bookmark bookmark = new Bookmark(BOOKMARK_ID, USER_ID, HREF, DESCRIPTION, LABEL);

        when(bookmarkRepository.findByIdAndUserId(BOOKMARK_ID, USER_ID)).thenReturn(Optional.of(bookmarkSpy));
        when(bookmarkRepository.save(bookmarkSpy)).thenReturn(bookmark);
        final Optional<Bookmark> result = bookmarkService1.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetails);

        assertThat(result.get(), is(bookmark));

        assertThat(bookmarkSpy.getHref(), is(HREF));
        assertThat(bookmarkSpy.getDescription(), is(DESCRIPTION));
        assertThat(bookmarkSpy.getLabel(), is(LABEL));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);

        when(bookmarkRepository.findByIdAndUserId(BOOKMARK_ID, USER_ID)).thenReturn(Optional.empty());
        final Optional<Bookmark> result = bookmarkService1.updateBookmark(USER_ID, BOOKMARK_ID, bookmarkDetails);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void shouldDeleteBookmarkWhenBookmarkExists() {
        final Bookmark bookmarkSpy = Mockito.spy(Bookmark.class);
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
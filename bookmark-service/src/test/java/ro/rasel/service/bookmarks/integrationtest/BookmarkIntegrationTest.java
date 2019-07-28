package ro.rasel.service.bookmarks.integrationtest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.service.bookmarks.dao.BookmarkRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;
import ro.rasel.service.bookmarks.domain.BookmarkDetails;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ro.rasel.service.bookmarks.utils.TestConstants.DESCRIPTION;
import static ro.rasel.service.bookmarks.utils.TestConstants.HREF;
import static ro.rasel.service.bookmarks.utils.TestConstants.LABEL;
import static ro.rasel.service.bookmarks.utils.TestConstants.USER_ID;

@SuppressWarnings({"ConstantConditions", "OptionalGetWithoutIsPresent", "SameParameterValue"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookmarkIntegrationTest {
    private static final String BOOKMARKS_ENDPOINT = "/v1/users/{userId}/bookmarks";
    private static final String BOOKMARK_ENDPOINT = "/v1/users/{userId}/bookmarks/{bookmarkId}";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @BeforeEach
    void before() {
        bookmarkRepository.deleteAll();
    }

    @Test
    void shouldGetBookmarkCollectionWhenBookmarksExist() {
        final Bookmark bookmark = initBookmarkInDb(USER_ID);

        final ResponseEntity<List<Bookmark>> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<Bookmark>>() {
                        }, USER_ID);

        assertThat(result.getBody().get(0), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyBookmarksCollectionWhenBookmarksDoesNotExist() {

        final ResponseEntity<List<Bookmark>> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<Bookmark>>() {
                        }, USER_ID);

        assertThat(result.getBody(), is((List<Bookmark>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetBookmarkWhenBookmarkExist() {
        final Bookmark bookmark = initBookmarkInDb(USER_ID);
        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, Bookmark.class,
                        USER_ID,
                        bookmark.getId());

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoBookmarkWhenBookmarksDoesNotExist() {
        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, Bookmark.class,
                        USER_ID,
                        3);

        assertThat(result.getBody(), is((Bookmark) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateBookmark() {
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);

        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.POST, new HttpEntity<>(bookmarkDetails),
                        Bookmark.class, USER_ID);

        final Bookmark bookmark = result.getBody();

        assertThat(bookmark.getUserId(), is(USER_ID));
        assertThat(bookmark.getHref(), is(HREF));
        assertThat(bookmark.getDescription(), is(DESCRIPTION));
        assertThat(bookmark.getLabel(), is(LABEL));

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

        assertThat(getBookmarkFromDb(bookmark.getId(), USER_ID).get(), is(bookmark));

    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final long bookmarkId = initBookmarkInDb(USER_ID, "0").getId();
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);
        final Bookmark bookmark =
                new Bookmark(bookmarkId, USER_ID, bookmarkDetails.getHref(), bookmarkDetails.getDescription(),
                        bookmarkDetails.getLabel());

        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(bookmarkDetails), Bookmark.class, USER_ID, bookmarkId);

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final BookmarkDetails bookmarkDetails = new BookmarkDetails(HREF, DESCRIPTION, LABEL);

        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(bookmarkDetails), Bookmark.class, USER_ID, 1);

        assertThat(result.getBody(), is((Bookmark) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteBookmarkWhenBookmarkExists() {
        final long bookmarkId = initBookmarkInDb(USER_ID).getId();

        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, Bookmark.class, USER_ID, bookmarkId);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteBookmarkAndBookmarkDoesNotExists() {
        final ResponseEntity<Bookmark> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, Bookmark.class, USER_ID, 1);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private Bookmark initBookmarkInDb(String userId) {
        return bookmarkRepository.save(createBookmark(userId));
    }

    private Bookmark initBookmarkInDb(String userId, String suffix) {
        return bookmarkRepository.save(createBookmark(userId, suffix));
    }

    private Optional<Bookmark> getBookmarkFromDb(long id, String userId) {
        return bookmarkRepository.findByIdAndUserId(id, userId);
    }

    private Bookmark createBookmark(String userId) {
        return createBookmark(userId, "");
    }

    private Bookmark createBookmark(String userId, String suffix) {
        return new Bookmark(userId,
                String.format("http://some-other-host-for-%s.com", userId + suffix),
                String.format("A description for %s's link", userId + suffix),
                String.format("%sLabel", userId + suffix));
    }
}
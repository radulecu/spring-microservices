package ro.rasel.spring.microservices.bookmarkservice.integrationtest.v1;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkDto;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkResponse;
import ro.rasel.spring.microservices.bookmarkservice.dao.BookmarkRepository;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.DESCRIPTION;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.HREF;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.LABEL;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.USER_ID;

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
    void beforeEach() {
        deleteAll();
    }

    @Test
    void shouldGetBookmarkCollectionWhenBookmarksExist() {
        final BookmarkResponse bookmarkResponse = new BookmarkResponse(initBookmarkInDb(USER_ID));

        final ResponseEntity<List<BookmarkResponse>> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<BookmarkResponse>>() {
                        }, USER_ID);

        assertThat(result.getBody().get(0), is(bookmarkResponse));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyBookmarksCollectionWhenBookmarksDoesNotExist() {

        final ResponseEntity<List<BookmarkResponse>> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<BookmarkResponse>>() {
                        }, USER_ID);

        assertThat(result.getBody(), is((List<BookmarkResponse>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetBookmarkWhenBookmarkExist() {
        final BookmarkResponse bookmark = new BookmarkResponse(initBookmarkInDb(USER_ID));
        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, BookmarkResponse.class,
                        USER_ID, bookmark.getId());

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoBookmarkWhenBookmarksDoesNotExist() {
        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, BookmarkResponse.class,
                        USER_ID, 3);

        assertThat(result.getBody(), is((BookmarkResponse) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateBookmark() {
        final BookmarkDto bookmarkDto = new BookmarkDto(HREF, DESCRIPTION, LABEL);

        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.POST, new HttpEntity<>(bookmarkDto),
                        BookmarkResponse.class, USER_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

        final BookmarkResponse bookmarkResponse = result.getBody();
        assertThat(bookmarkResponse.getUserId(), is(USER_ID));
        assertThat(bookmarkResponse.getHref(), is(HREF));
        assertThat(bookmarkResponse.getDescription(), is(DESCRIPTION));
        assertThat(bookmarkResponse.getLabel(), is(LABEL));

        assertThat(getBookmarkFromDb(bookmarkResponse.getId(), USER_ID).get(), is(bookmarkResponse.getBookmark()));

    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final long bookmarkId = initBookmarkInDb(USER_ID, "0").getId();
        final BookmarkDto bookmarkDto = new BookmarkDto(HREF, DESCRIPTION, LABEL);
        final BookmarkResponse bookmark =
                new BookmarkResponse(bookmarkId, USER_ID, bookmarkDto.getHref(), bookmarkDto.getDescription(),
                        bookmarkDto.getLabel());

        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(bookmarkDto), BookmarkResponse.class, USER_ID, bookmarkId);

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final BookmarkDto bookmarkDto = new BookmarkDto(HREF, DESCRIPTION, LABEL);

        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(bookmarkDto), BookmarkResponse.class, USER_ID, 1);

        assertThat(result.getBody(), is((BookmarkResponse) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteBookmarkWhenBookmarkExists() {
        final long bookmarkId = initBookmarkInDb(USER_ID).getId();

        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, BookmarkResponse.class, USER_ID, bookmarkId);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteBookmarkAndBookmarkDoesNotExists() {
        final ResponseEntity<BookmarkResponse> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, BookmarkResponse.class, USER_ID, 1);

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

    public void deleteAll() {
        bookmarkRepository.deleteAll();
    }
}
package ro.rasel.spring.microservices.bookmarkservice.integrationtest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.rasel.spring.microservices.bookmarkservice.controller.dto.BookmarkDetailsDto;
import ro.rasel.spring.microservices.bookmarkservice.controller.dto.BookmarkDto;
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
        final BookmarkDto bookmarkDto = new BookmarkDto(initBookmarkInDb(USER_ID));

        final ResponseEntity<List<BookmarkDto>> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<BookmarkDto>>() {
                        }, USER_ID);

        assertThat(result.getBody().get(0), is(bookmarkDto));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetEmptyBookmarksCollectionWhenBookmarksDoesNotExist() {

        final ResponseEntity<List<BookmarkDto>> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<BookmarkDto>>() {
                        }, USER_ID);

        assertThat(result.getBody(), is((List<BookmarkDto>) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldGetBookmarkWhenBookmarkExist() {
        final BookmarkDto bookmark = new BookmarkDto(initBookmarkInDb(USER_ID));
        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, BookmarkDto.class,
                        USER_ID, bookmark.getId());

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldGetNoBookmarkWhenBookmarksDoesNotExist() {
        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.GET, HttpEntity.EMPTY, BookmarkDto.class,
                        USER_ID, 3);

        assertThat(result.getBody(), is((BookmarkDto) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldCreateBookmark() {
        final BookmarkDetailsDto bookmarkDetailsDto = new BookmarkDetailsDto(HREF, DESCRIPTION, LABEL);

        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARKS_ENDPOINT, HttpMethod.POST, new HttpEntity<>(bookmarkDetailsDto),
                        BookmarkDto.class, USER_ID);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

        final BookmarkDto bookmarkDto = result.getBody();
        assertThat(bookmarkDto.getUserId(), is(USER_ID));
        assertThat(bookmarkDto.getHref(), is(HREF));
        assertThat(bookmarkDto.getDescription(), is(DESCRIPTION));
        assertThat(bookmarkDto.getLabel(), is(LABEL));

        assertThat(getBookmarkFromDb(bookmarkDto.getId(), USER_ID).get(), is(bookmarkDto.getBookmark()));

    }

    @Test
    void shouldUpdateBookmarkWhenBookmarkExists() {
        final long bookmarkId = initBookmarkInDb(USER_ID, "0").getId();
        final BookmarkDetailsDto bookmarkDetailsDto = new BookmarkDetailsDto(HREF, DESCRIPTION, LABEL);
        final BookmarkDto bookmark =
                new BookmarkDto(bookmarkId, USER_ID, bookmarkDetailsDto.getHref(), bookmarkDetailsDto.getDescription(),
                        bookmarkDetailsDto.getLabel());

        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(bookmarkDetailsDto), BookmarkDto.class, USER_ID, bookmarkId);

        assertThat(result.getBody(), is(bookmark));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingUpdateBookmarkAndBookmarkDoesNotExists() {
        final BookmarkDetailsDto bookmarkDetailsDto = new BookmarkDetailsDto(HREF, DESCRIPTION, LABEL);

        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.PUT,
                        new HttpEntity<>(bookmarkDetailsDto), BookmarkDto.class, USER_ID, 1);

        assertThat(result.getBody(), is((BookmarkDto) null));
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    void shouldDeleteBookmarkWhenBookmarkExists() {
        final long bookmarkId = initBookmarkInDb(USER_ID).getId();

        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, BookmarkDto.class, USER_ID, bookmarkId);

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void shouldReturnNotFoundStatusCodeWhenCallingDeleteBookmarkAndBookmarkDoesNotExists() {
        final ResponseEntity<BookmarkDto> result = testRestTemplate
                .exchange(BOOKMARK_ENDPOINT, HttpMethod.DELETE,
                        HttpEntity.EMPTY, BookmarkDto.class, USER_ID, 1);

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
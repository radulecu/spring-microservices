package ro.rasel.spring.microservices.bookmarkservice.repository;

import org.hamcrest.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.rasel.spring.microservices.bookmarkservice.dao.BookmarkRepository;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.hamcrest.core.Is.*;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.DESCRIPTION;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.HREF;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.LABEL;
import static ro.rasel.spring.microservices.bookmarkservice.utils.TestConstants.USER_ID;

@SpringBootTest
public class BookmarkRepositoryTest {
    @Autowired
    BookmarkRepository bookmarkRepository;

    @BeforeEach()
    public void beforeEach() {
        bookmarkRepository.deleteAll();
    }

    @Test
    public void shouldFindByIdAndUserIdWhenBookmarkIsCreated() {
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL));
        final Optional<Bookmark> byIdAndUserId =
                bookmarkRepository.findByIdAndUserId(bookmark.getId(), bookmark.getUserId());
        MatcherAssert.assertThat(byIdAndUserId.isPresent(), is(true));
        MatcherAssert.assertThat(byIdAndUserId.get(), is(bookmark));
    }

    @Test
    public void shouldFindByUserIdWhenBookmarkIsCreated() {
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL));
        final Bookmark bookmark2 = bookmarkRepository.save(new Bookmark(USER_ID, HREF + 2, DESCRIPTION + 2, LABEL + 2));
        final List<Bookmark> byUserId = bookmarkRepository.findByUserId(USER_ID);
        MatcherAssert.assertThat(byUserId, containsInAnyOrder(bookmark, bookmark2));
    }

    @Test
    public void shouldFindByUserIdAndLabelWhenBookmarkIsCreated() {
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL));
        final List<Bookmark> byUserId = bookmarkRepository.findByUserIdAndLabel(USER_ID, LABEL);
        MatcherAssert.assertThat(byUserId, containsInAnyOrder(bookmark));
    }

    @Test
    public void shouldDeleteWhenBookmarkIsCreated() {
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark(USER_ID, HREF, DESCRIPTION, LABEL));
        bookmarkRepository.delete(bookmark);
        final Optional<Bookmark> byIdAndUserId = bookmarkRepository.findByIdAndUserId(bookmark.getId(), USER_ID);
        MatcherAssert.assertThat(byIdAndUserId.isPresent(), is(false));
    }
}

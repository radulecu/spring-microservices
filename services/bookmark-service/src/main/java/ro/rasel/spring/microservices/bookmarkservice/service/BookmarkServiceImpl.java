package ro.rasel.spring.microservices.bookmarkservice.service;

import org.springframework.stereotype.Service;
import ro.rasel.spring.microservices.bookmarkservice.dao.BookmarkRepository;
import ro.rasel.spring.microservices.bookmarkservice.domain.Bookmark;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public List<Bookmark> getBookmarks(String userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    @Override
    public Optional<Bookmark> getBookmark(String userId, long bookmarkId) {
        return bookmarkRepository.findByIdAndUserId(bookmarkId, userId);
    }

    @Override
    public Bookmark createBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    @Transactional
    public Optional<Bookmark> updateBookmark(Bookmark bookmark) {
        final Optional<Bookmark> currentBookmark =
                bookmarkRepository.findByIdAndUserId(bookmark.getId(), bookmark.getUserId());

        return currentBookmark.map(b -> {
            b.setDescription(bookmark.getDescription());
            b.setHref(bookmark.getHref());
            b.setLabel(bookmark.getLabel());
            return bookmarkRepository.save(b);
        });
    }

    @Override
    @Transactional
    public boolean deleteBookmark(String userId, long bookmarkId) {
        final Optional<Bookmark> bookmark = bookmarkRepository.findByIdAndUserId(bookmarkId, userId);
        bookmark.ifPresent(bookmarkRepository::delete);
        return bookmark.isPresent();
    }
}

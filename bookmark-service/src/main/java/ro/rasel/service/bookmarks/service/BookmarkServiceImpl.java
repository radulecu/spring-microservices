package ro.rasel.service.bookmarks.service;

import org.springframework.stereotype.Service;
import ro.rasel.service.bookmarks.dao.BookmarkRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;

import java.util.List;

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
    public Bookmark getBookmark(String userId, Long bookmarkId) {
        return bookmarkRepository.findByIdAndUserId(bookmarkId, userId);
    }

    @Override
    public Bookmark createBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public boolean deleteBookmark(String userId, Long bookmarkId) {
        return bookmarkRepository.deleteByIdAndUserId(bookmarkId, userId) >=1;
    }
}

package ro.rasel.service.bookmarks.controler;

import org.springframework.web.bind.annotation.*;
import ro.rasel.service.bookmarks.dao.BookmarkRepository;
import ro.rasel.service.bookmarks.domain.Bookmark;

import java.util.Collection;

@RestController
@RequestMapping("/bookmarks/{userId}")
public class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkRestController(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Bookmark> getBookmarks(@PathVariable String userId) {
        return this.bookmarkRepository.findByUserId(userId);
    }

    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    public Bookmark getBookmark(@PathVariable String userId,
                                @PathVariable Long bookmarkId) {
        return this.bookmarkRepository.findByUserIdAndId(userId, bookmarkId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Bookmark createBookmark(@PathVariable String userId,
                                   @RequestBody Bookmark bookmark) {
        Bookmark bookmarkInstance = new Bookmark(userId, bookmark.getHref(),
                bookmark.getDescription(), bookmark.getLabel());
        return this.bookmarkRepository.save(bookmarkInstance);
    }



}

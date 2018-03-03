package ro.rasel.service.passport.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.service.passport.domain.Bookmark;

import java.util.Collection;

@FeignClient("bookmark-service")
public interface BookmarkClient {

    @GetMapping(value = "/bookmarks/{userId}")
    Collection<Bookmark> getBookmarks(@PathVariable("userId") String userId);

}

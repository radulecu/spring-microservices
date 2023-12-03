package ro.rasel.client.service.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.rasel.client.service.domain.Bookmark;

import java.util.Collection;

@FeignClient("bookmark-service")
public interface BookmarkClient {

    @RequestMapping(method = RequestMethod.GET, value = "/bookmarks/{userId}")
    Collection<Bookmark> getBookmarks(@PathVariable("userId") String userId);

}

package ro.rasel.client.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("bookmark-service")
public interface BookmarkClient {

    @GetMapping(value = "/users/{userId}/bookmarks")
    String getBookmarksAsString(@PathVariable("userId") String userId);

}

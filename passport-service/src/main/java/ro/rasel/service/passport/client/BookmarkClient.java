package ro.rasel.service.passport.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.bookmark.api.BookmarkApi;

@FeignClient("bookmark-service")
public interface BookmarkClient extends BookmarkApi {

}

package ro.rasel.spring.microserivces.passportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.spring.microserivces.api.bookmark.BookmarkApi;

@FeignClient("bookmark-service")
public interface BookmarkClient extends BookmarkApi {

}

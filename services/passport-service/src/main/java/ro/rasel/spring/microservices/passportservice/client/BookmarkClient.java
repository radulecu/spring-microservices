package ro.rasel.spring.microservices.passportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.spring.microservices.api.bookmark.BookmarkApi;

@FeignClient("bookmark-service")
public interface BookmarkClient extends BookmarkApi {

}

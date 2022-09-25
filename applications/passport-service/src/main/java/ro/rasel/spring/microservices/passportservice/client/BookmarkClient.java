package ro.rasel.spring.microservices.passportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import ro.rasel.spring.microservices.api.bookmark.BookmarkApi;

@FeignClient(name="bookmark-service", url="${feign.client.bookmark-service.url:}")
public interface BookmarkClient extends BookmarkApi {

}

package ro.rasel.spring.microservices.bookmarkservice.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkRequest;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkResponse;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/v1/users/{userId}/bookmarks")
@Api(tags = BookmarkApi.TAG)
public interface BookmarkApi {
    String TAG = "bookmark";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get bookmarks", notes = "Get a list of bookmarks", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<Collection<BookmarkResponse>> getBookmarks(@PathVariable String userId);

    @GetMapping("/{bookmarkId}")
    @ApiOperation(value = "get bookmark", notes = "Get a bookmark", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<BookmarkResponse> getBookmark(@PathVariable String userId, @PathVariable long bookmarkId);

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add bookmark", notes = "Create a new bookmark and return it", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    BookmarkResponse createBookmark(@PathVariable String userId, @Valid @RequestBody BookmarkRequest bookmarkRequest);

    @PutMapping(value = "/{bookmarkId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add bookmark", notes = "Update a bookmark and return it", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<BookmarkResponse> updateBookmark(
            @PathVariable String userId, @PathVariable long bookmarkId,
            @Valid @RequestBody BookmarkRequest bookmarkRequest);

    @DeleteMapping(value = "/{bookmarkId}")
    @ApiOperation(code = 201, value = "delete bookmarks", notes = "Delete a bookmark", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 201, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<Void> deleteBookmark(@PathVariable String userId, @PathVariable long bookmarkId);
}

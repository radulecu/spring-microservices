package ro.rasel.spring.microservices.bookmarkservice.controller.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkRequest;
import ro.rasel.spring.microservices.bookmarkservice.controller.v1.dto.BookmarkResponse;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ro.rasel.spring.microservices.bookmarkservice.controller.v1.BookmarkApi.NAME;

@RequestMapping(value = "/v1/users/{userId}/bookmarks")
@OpenAPIDefinition(tags = @Tag(name = BookmarkApi.NAME, description = NAME + " REST controller"))
public interface BookmarkApi {
    String NAME = "Bookmark";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get bookmarks", description = "Get a list of bookmarks", tags = NAME)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Collection<BookmarkResponse>> getBookmarks(@PathVariable String userId);

    @GetMapping("/{bookmarkId}")
    @Operation(summary = "Get bookmark", description = "Get a bookmark", tags = NAME)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<BookmarkResponse> getBookmark(@PathVariable String userId, @PathVariable long bookmarkId);

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Add bookmark", description = "Create a new bookmark and return it", tags = NAME)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    BookmarkResponse createBookmark(@PathVariable String userId, @Valid @RequestBody BookmarkRequest bookmarkRequest);

    @PutMapping(value = "/{bookmarkId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update bookmark", description = "Update a bookmark and return it", tags = NAME)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<BookmarkResponse> updateBookmark(
            @PathVariable String userId, @PathVariable long bookmarkId,
            @Valid @RequestBody BookmarkRequest bookmarkRequest);

    @DeleteMapping(value = "/{bookmarkId}")
    @Operation(summary = "Delete bookmarks", description = "Delete a bookmark", tags = NAME)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted, No content"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteBookmark(@PathVariable String userId, @PathVariable long bookmarkId);
}

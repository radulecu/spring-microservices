package ro.rasel.service.contacts.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.rasel.service.contacts.domain.Contact;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/v1/users/{userId}/contacts", produces = APPLICATION_JSON_VALUE)
@Api(tags = ContactApi.TAG)
public interface ContactApi {
    String TAG = "contact";

    @GetMapping
    @ApiOperation(value = "get contacts", notes = "Get a list of contacts", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<Collection<Contact>> getContacts(@PathVariable String userId);

    @GetMapping("/{contactId}")
    @ApiOperation(value = "get contact", notes = "Get a contact", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<Contact> getContact(@PathVariable String userId, @PathVariable long contactId);

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add contact", notes = "Create a new contact and return it", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    Contact createContact(@PathVariable String userId, @RequestBody Contact contact);

    @DeleteMapping(value = "/{contactId}", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(code = 201, value = "delete contact", notes = "Delete a contact", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<Void> deleteContact(@PathVariable String userId, @RequestBody long contactId);
}

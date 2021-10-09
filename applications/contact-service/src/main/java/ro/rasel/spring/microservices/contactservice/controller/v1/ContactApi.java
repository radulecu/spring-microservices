package ro.rasel.spring.microservices.contactservice.controller.v1;

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
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactResponse;

import javax.validation.Valid;
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
    ResponseEntity<Collection<ContactResponse>> getContacts(@PathVariable String userId);

    @GetMapping(value = "/{contactId}")
    @ApiOperation(value = "get contact", notes = "Get a contact", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<ContactResponse> getContact(@PathVariable String userId, @PathVariable long contactId);

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add contact", notes = "Create a new contact and return it", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ContactResponse createContact(@PathVariable String userId, @Valid @RequestBody ContactRequest contactRequest);

    @PutMapping(value = "/{contactId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add contact", notes = "Update a contact and return it", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<ContactResponse> updateContact(
            @PathVariable String userId, @PathVariable long contactId,
            @Valid @RequestBody ContactRequest contactRequest);

    @DeleteMapping(value = "/{contactId}")
    @ApiOperation(code = 201, value = "delete contact", notes = "Delete a contact", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 201, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<Void> deleteContact(@PathVariable String userId, @PathVariable long contactId);
}

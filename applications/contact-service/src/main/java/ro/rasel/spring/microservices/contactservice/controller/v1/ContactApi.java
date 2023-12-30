package ro.rasel.spring.microservices.contactservice.controller.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactRequest;
import ro.rasel.spring.microservices.contactservice.controller.v1.dto.ContactResponse;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/v1/users/{userId}/contacts", produces = APPLICATION_JSON_VALUE)
@OpenAPIDefinition(tags = @Tag(name = ContactApi.NAME, description = ContactApi.NAME + " REST controller"))
public interface ContactApi {
    String NAME = "Contact";

    @GetMapping
    @Operation(summary = "Get contacts", description = "Get a list of contacts", tags = NAME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Collection<ContactResponse>> getContacts(@PathVariable String userId);

    @GetMapping(value = "/{contactId}")
    @Operation(summary = "Get contact", description = "Get a contact", tags = NAME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<ContactResponse> getContact(@PathVariable String userId, @PathVariable long contactId);

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Add contact", description = "Create a new contact and return it", tags = NAME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ContactResponse createContact(@PathVariable String userId, @Valid @RequestBody ContactRequest contactRequest);

    @PutMapping(value = "/{contactId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update contact", description = "Update a contact and return it", tags = NAME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<ContactResponse> updateContact(
            @PathVariable String userId, @PathVariable long contactId,
            @Valid @RequestBody ContactRequest contactRequest);

    @DeleteMapping(value = "/{contactId}")
    @Operation(summary = "Delete contact", description = "Delete a contact", tags = NAME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Void> deleteContact(@PathVariable String userId, @PathVariable long contactId);
}

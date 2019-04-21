package ro.rasel.service.contacts.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.contacts.dao.ContactRepository;
import ro.rasel.service.contacts.domain.Contact;

import java.util.Collection;

@RestController
@RequestMapping(value = "/users/{userId}/contacts", produces = "application/json")
public class ContactRestController {

    private final ContactRepository contactRepository;

    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping
    @ApiOperation(value = "Get a list of contacts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Collection<Contact> getContacts(@PathVariable String userId) {
        return this.contactRepository.findByUserId(userId);
    }

    @GetMapping(value = "/{contactId}")
    @ApiOperation("Get a contact")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Contact getContact(@PathVariable String userId, @PathVariable Long contactId) {
        return this.contactRepository.findByUserIdAndId(userId, contactId);
    }

    @PostMapping
    @ApiOperation("Create a new contact and return it")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Contact createContact(@PathVariable String userId,
            @RequestBody Contact contact) {
        return this.contactRepository.save(new Contact(userId, contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getRelationship()));
    }

}

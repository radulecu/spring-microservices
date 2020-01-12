package ro.rasel.spring.microservices.clientservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.api.contact.domain.Contact;
import ro.rasel.spring.microservices.clientservice.client.IntegrationClient;
import ro.rasel.spring.microservices.clientservice.domain.Passport;

import java.util.Collection;

@RestController
@RequestMapping(produces = "application/json")
class ClientRestController {

    private final IntegrationClient integrationClient;

    @Autowired
    ClientRestController(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @GetMapping("/users/{userId}/passport")
    @ApiOperation(value = "Get passport")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    Passport passport(@PathVariable String userId) {
        return integrationClient.getPassport(userId);
    }

    @GetMapping("/users/{userId}/bookmarks")
    @ApiOperation(value = "Get a list of bookmarks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    String bookmarks(@PathVariable String userId) {
        return integrationClient.getBookmarks(userId);
    }

    @GetMapping("/users/{userId}/contacts")
    @ApiOperation(value = "Get a list of contacts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    Collection<Contact> contacts(@PathVariable String userId) {
        return integrationClient.getContacts(userId);
    }
}

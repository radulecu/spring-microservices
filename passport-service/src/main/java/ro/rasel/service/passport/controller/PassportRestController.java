package ro.rasel.service.passport.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.service.passport.client.IntegrationClient;
import ro.rasel.service.passport.domain.Passport;

@RestController
@RequestMapping(value = "/users/{userId}/passport", produces = "application/json")
public class PassportRestController {

    private final IntegrationClient integrationClient;

    public PassportRestController(IntegrationClient integrationClient) {
        this.integrationClient = integrationClient;
    }

    @GetMapping
    @ApiOperation(value = "Get passport")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Passport passport(@PathVariable String userId) {
        return new Passport(userId, this.integrationClient.getContacts(userId),
                this.integrationClient.getBookmarks(userId));
    }

}

package ro.rasel.service.passport.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.service.passport.domain.Passport;

@Api(tags = PassportApi.TAG)
public interface PassportApi {

    String TAG = "passport";

    @ApiOperation(value = "Get passport", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(value = "/v1/users/{userId}/passport", produces = "application/json")
    ResponseEntity<Passport> passport(@PathVariable String userId);
}

package ro.rasel.spring.microservices.passportservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.rasel.spring.microservices.passportservice.controller.dto.PassportResponse;

import java.util.concurrent.Future;

@Api(tags = AsyncPassportApi.TAG)
public interface AsyncPassportApi {

    String TAG = "passport";

    @ApiOperation(value = "Get passport", tags = TAG, response = PassportResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(value = "/v1/async/users/{userId}/passport", produces = "application/json")
    Future<ResponseEntity<PassportResponse>> getPassport(@PathVariable String userId);
}

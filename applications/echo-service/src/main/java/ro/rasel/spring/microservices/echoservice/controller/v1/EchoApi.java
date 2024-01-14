package ro.rasel.spring.microservices.echoservice.controller.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoRequest;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = {"/${echo.root.path:echo}/**"})
@OpenAPIDefinition(tags = @Tag(name = EchoApi.NAME, description = EchoApi.NAME + " REST controller"))
public interface EchoApi {
    String NAME = "echo";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get echo", description = "Echo on a GET request", tags = NAME)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<EchoResponse> getEchos(
            @RequestHeader HttpHeaders headers,
            @RequestParam(value = "responseStatus", defaultValue = "200") Integer responseStatusParam,
            @RequestParam(value = "responseHeaders", defaultValue = "") List<String> responseHeadersParam,
            HttpServletRequest httpServletRequest);

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Post echo", description = "Echo on a POST request", tags = NAME)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<EchoResponse> createEcho(
            @Valid @RequestBody EchoRequest echoRequest,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest httpServletRequest);

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Put echo", description = "Echo on a PUT request", tags = NAME)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<EchoResponse> updateEcho(
            @Valid @RequestBody EchoRequest echoRequest,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest httpServletRequest);

    @DeleteMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete echo", description = "Echo on a delete request", tags = NAME)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok")})
    ResponseEntity<EchoResponse> deleteEcho(
            @Valid @RequestBody EchoRequest echoRequest,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest httpServletRequest);

}

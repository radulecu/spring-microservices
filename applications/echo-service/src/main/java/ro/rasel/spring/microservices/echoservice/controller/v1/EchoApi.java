package ro.rasel.spring.microservices.echoservice.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoRequest;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = {"/${echo.root.path:echo}/**"})
@Api(tags = EchoApi.TAG)
public interface EchoApi {
    String TAG = "echo";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get echo", notes = "Get echo", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok")})
    ResponseEntity<EchoResponse> getEchos(
            @RequestHeader HttpHeaders headers,
            @RequestParam(value = "responseStatus", defaultValue = "200") Integer responseStatusParam,
            @RequestParam(value = "responseHeaders", defaultValue = "") List<String> responseHeadersParam,
            HttpServletRequest httpServletRequest);

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "post echo", notes = "Post echo", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok")})
    ResponseEntity<EchoResponse> createEcho(
            @Valid @RequestBody EchoRequest echoRequest,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest httpServletRequest);

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "put echo", notes = "put echo", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok")})
    ResponseEntity<EchoResponse> updateEcho(
            @Valid @RequestBody EchoRequest echoRequest,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest httpServletRequest);

    @DeleteMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete echo", notes = "delete echo", tags = TAG)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok")})
    ResponseEntity<EchoResponse> deleteEcho(
            @Valid @RequestBody EchoRequest echoRequest,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest httpServletRequest);

}

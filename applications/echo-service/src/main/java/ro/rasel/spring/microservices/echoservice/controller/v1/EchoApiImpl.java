package ro.rasel.spring.microservices.echoservice.controller.v1;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoRequest;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EchoApiImpl implements EchoApi {
    @Override
    public ResponseEntity<EchoResponse> getEchos(
            HttpHeaders requestHeaders, Integer responseStatus,
            HttpServletRequest httpServletRequest) {
        return processEcho(new EchoRequest(responseStatus, Collections.emptyMap()),
                httpServletRequest.getParameterMap(), requestHeaders);
    }


    @Override
    public ResponseEntity<EchoResponse> createEcho(
            @Valid EchoRequest echoRequest, HttpHeaders headers, HttpServletRequest httpServletRequest) {
        return processEcho(echoRequest, httpServletRequest.getParameterMap(), headers);
    }

    @Override
    public ResponseEntity<EchoResponse> updateEcho(
            @Valid EchoRequest echoRequest, HttpHeaders headers, HttpServletRequest httpServletRequest) {
        return processEcho(echoRequest, httpServletRequest.getParameterMap(), headers);
    }

    @Override
    public ResponseEntity<EchoResponse> deleteEcho(
            @Valid EchoRequest echoRequest, HttpHeaders headers, HttpServletRequest httpServletRequest) {
        return processEcho(echoRequest, httpServletRequest.getParameterMap(), headers);
    }

    private ResponseEntity<EchoResponse> processEcho(
            EchoRequest echoRequest, Map<String, String[]> requestParameters, HttpHeaders requestHeaders) {
        return ResponseEntity.status(echoRequest.getResponseStatus())
                .headers(asHttpHeaders(echoRequest.getResponseHeaders()))
                .body(new EchoResponse(
                        asListValueMap(requestParameters),
                        asListValueMap(requestHeaders)));
    }

    private Map<String, List<String>> asListValueMap(Map<String, String[]> parameters) {
        return parameters.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Arrays.asList(e.getValue())));
    }

    private Map<String, List<String>> asListValueMap(HttpHeaders headers) {
        return headers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private HttpHeaders asHttpHeaders(Map<String, List<String>> headersMap) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        headersMap.forEach(httpHeaders::addAll);
        return httpHeaders;
    }
}

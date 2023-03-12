package ro.rasel.spring.microservices.echoservice.controller.v1;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoRequest;
import ro.rasel.spring.microservices.echoservice.controller.v1.dao.EchoResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EchoApiImpl implements EchoApi {
    @Override
    public ResponseEntity<EchoResponse> getEchos(
            HttpHeaders requestHeaders,
            Integer responseStatusParam,
            List<String> responseHeadersParam,
            HttpServletRequest httpServletRequest) {
        return processEcho(
                httpServletRequest.getParameterMap(),
                requestHeaders,
                extractResponseHeadersFromParameters(responseHeadersParam),
                responseStatusParam);
    }

    @Override
    public ResponseEntity<EchoResponse> createEcho(
            @Valid EchoRequest echoRequest,
            HttpHeaders headers,
            HttpServletRequest httpServletRequest) {
        return processEcho(httpServletRequest.getParameterMap(),
                headers,
                echoRequest.getResponseHeaders(),
                echoRequest.getResponseStatus());
    }

    @Override
    public ResponseEntity<EchoResponse> updateEcho(
            @Valid EchoRequest echoRequest,
            HttpHeaders headers,
            HttpServletRequest httpServletRequest) {
        return processEcho(httpServletRequest.getParameterMap(),
                headers,
                echoRequest.getResponseHeaders(),
                echoRequest.getResponseStatus());
    }

    @Override
    public ResponseEntity<EchoResponse> deleteEcho(
            @Valid EchoRequest echoRequest,
            HttpHeaders headers,
            HttpServletRequest httpServletRequest) {
        return processEcho(httpServletRequest.getParameterMap(),
                headers,
                echoRequest.getResponseHeaders(),
                echoRequest.getResponseStatus());
    }

    private static Map<String, List<String>> extractResponseHeadersFromParameters(List<String> responseHeaders) {
        return responseHeaders.stream()
                .filter(s -> s.contains("="))
                .map(s -> s.split("=", 2))
                .map(ss -> (Pair<String, String>) new ImmutablePair<>(ss[0], ss[1]))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));
    }

    private ResponseEntity<EchoResponse> processEcho(
            Map<String, String[]> requestParameters,
            HttpHeaders requestHeaders,
            Map<String, List<String>> responseHeaders,
            int responseStatus) {
        return ResponseEntity.status(responseStatus)
                .headers(asHttpHeaders(responseHeaders))
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

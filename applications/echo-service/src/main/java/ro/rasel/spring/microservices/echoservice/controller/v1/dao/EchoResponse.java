package ro.rasel.spring.microservices.echoservice.controller.v1.dao;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Schema(description = "Response for echo request")
public class EchoResponse {
    private final Map<String, List<String>> requestHeaders;
    private final Map<String, List<String>> requestParameters;

    public EchoResponse(
            Map<String, List<String>> requestParameters, Map<String, List<String>> requestHeaders) {
        this.requestHeaders = requestHeaders;
        this.requestParameters = requestParameters;
    }

    public Map<String, List<String>> getRequestHeaders() {
        return requestHeaders;
    }

    public Map<String, List<String>> getRequestParameters() {
        return requestParameters;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EchoResponse.class.getSimpleName() + "[", "]")
                .add("requestHeaders=" + requestHeaders)
                .add("requestParameters=" + requestParameters)
                .toString();
    }
}

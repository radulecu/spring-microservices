package ro.rasel.spring.microservices.echoservice.controller.v1.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.beans.ConstructorProperties;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@ApiModel
public class EchoRequest {
    private final Map<String, List<String>> responseHeaders;
    private final int responseStatus;

    @ConstructorProperties({"responseStatus", "responseHeaders"})
    public EchoRequest(Integer responseStatus, Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders != null ? responseHeaders : Collections.emptyMap();
        this.responseStatus = responseStatus != null ? responseStatus : 200;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    @ApiModelProperty(example = "200")
    public int getResponseStatus() {
        return responseStatus;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EchoRequest.class.getSimpleName() + "[", "]")
                .add("responseHeaders=" + responseHeaders)
                .add("responseStatus=" + responseStatus)
                .toString();
    }
}

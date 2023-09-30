package ro.rasel.spring.microservices.ssoauthservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtOpenidConfigurationResponse {
    private final String issuer;
    private final String jwksUri;

    public JwtOpenidConfigurationResponse(String issuer, String jwksUri) {
        this.issuer = issuer;
        this.jwksUri = jwksUri;
    }

    @JsonProperty("jwks_uri")
    public String getJwksUri() {
        return issuer + jwksUri;
    }

    public String getIssuer() {
        return issuer;
    }
}

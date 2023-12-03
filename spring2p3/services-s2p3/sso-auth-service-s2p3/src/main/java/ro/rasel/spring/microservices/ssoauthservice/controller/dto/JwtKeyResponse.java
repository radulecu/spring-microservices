package ro.rasel.spring.microservices.ssoauthservice.controller.dto;

import com.nimbusds.jose.jwk.JWK;

import java.util.Arrays;
import java.util.List;

public class JwtKeyResponse {
    private List<JWK> keys;

    public JwtKeyResponse(JWK... keys) {
        this.keys = Arrays.asList(keys);
    }

    public List<JWK> getKeys() {
        return keys;
    }
}

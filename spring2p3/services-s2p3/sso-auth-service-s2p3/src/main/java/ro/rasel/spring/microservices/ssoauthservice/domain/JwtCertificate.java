package ro.rasel.spring.microservices.ssoauthservice.domain;

import com.nimbusds.jose.jwk.JWK;

import java.security.KeyPair;

public class JwtCertificate {

    private final String issuer;
    private final JWK jwk;
    private final KeyPair keyPair;

    public JwtCertificate(String issuer, JWK jwk, KeyPair keyPair) {
        this.issuer = issuer;
        this.jwk = jwk;
        this.keyPair = keyPair;
    }

    public String getIssuer() {
        return issuer;
    }

    public JWK jwk() {
        return jwk;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}

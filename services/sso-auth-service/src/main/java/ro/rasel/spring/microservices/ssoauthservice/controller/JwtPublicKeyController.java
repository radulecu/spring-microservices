package ro.rasel.spring.microservices.ssoauthservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.rasel.spring.microservices.ssoauthservice.controller.dto.JwtKeyResponse;
import ro.rasel.spring.microservices.ssoauthservice.controller.dto.JwtOpenidConfigurationResponse;
import ro.rasel.spring.microservices.ssoauthservice.controller.dto.PublicKeyResponse;
import ro.rasel.spring.microservices.ssoauthservice.domain.JwtCertificate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
public class JwtPublicKeyController {
    private final JwtCertificate jwtCertificate;

    public JwtPublicKeyController(JwtCertificate jwtCertificate) {
        this.jwtCertificate = jwtCertificate;
    }

    @GetMapping(path = "/.well-known/openid-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtOpenidConfigurationResponse getOpenidConfiguration() {
        return new JwtOpenidConfigurationResponse(jwtCertificate.getIssuer(), "/key");
    }

    @GetMapping(path = "/api/key", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtKeyResponse getJwtKey() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String invalidJson ="{ private: { a: adasdas, b: asdasdasd, c: asdasdas} }";
        invalidJson = (invalidJson .replaceAll(":\"?([^{|^]*?)\"?(?=[,|}|\\]])",":\"$1\"").replaceAll("\"?(\\w+)\"?(?=:)","\"$1\""));
        Map<String, Object> theMap = objectMapper.readValue(invalidJson , Map.class);
        System.out.println(theMap.get("private"));

        return new JwtKeyResponse(jwtCertificate.jwk());
    }


    @RequestMapping(path = "/api/publicKey", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PublicKeyResponse getPublicJwtKey() {
        byte[] publicKeyBytes = jwtCertificate.getKeyPair().getPublic().getEncoded();
        byte[] base64PublicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        String publicKey = new String(base64PublicKeyBytes, StandardCharsets.UTF_8);
        return new PublicKeyResponse(publicKey);
    }
}

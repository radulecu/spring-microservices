package ro.rasel.spring.microservices.component.securityclient.resource.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.rasel.spring.microservices.common.utils.properties.securityclient.SecurityConfigProperties;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class PublicKeyLoaderService {
    private final SecurityConfigProperties securityConfigProperties;

    public PublicKeyLoaderService(SecurityConfigProperties securityConfigProperties) {
        this.securityConfigProperties = securityConfigProperties;
    }

    public RSAPublicKey loadSigningKey() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> forEntity =
                restTemplate.getForEntity(securityConfigProperties.getUrl() + "/publicKey", Map.class);
        Map<String, Object> map = forEntity.getBody();
        String key = (String) map.get("base64X509Cert");
        byte[] base64KeyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = Base64.getDecoder().decode(base64KeyBytes);

        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));

            return rsaPublicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}

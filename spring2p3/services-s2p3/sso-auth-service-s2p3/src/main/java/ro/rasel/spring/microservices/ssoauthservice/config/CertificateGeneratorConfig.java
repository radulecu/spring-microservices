package ro.rasel.spring.microservices.ssoauthservice.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.ssoauthservice.config.properites.SecurityAuthorizationConfig;
import ro.rasel.spring.microservices.ssoauthservice.domain.JwtCertificate;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class CertificateGeneratorConfig {
//    @Bean
//    public static KeyPair keyPair() throws NoSuchAlgorithmException {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.generateKeyPair();
//        return keyPairGenerator.generateKeyPair();
//    }
//
//    @Bean
//    public static JwtCertificate jwkKey(SecurityAuthorizationConfig securityAuthorizationConfig)
//            throws JOSEException, CertificateException, NoSuchAlgorithmException, SignatureException,
//            InvalidKeyException, NoSuchProviderException, IOException {
//        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
//        certAndKeyGen.generate(2048);
//
//        X500Name x500Name =
//                new X500Name(securityAuthorizationConfig.getIssuer(), "OU", "O", "BUCHAREST", "ROMANIA", "ROMANIA");
//        KeyPair keyPair = new KeyPair(certAndKeyGen.getPublicKey(), certAndKeyGen.getPrivateKey());
//        JWK jwk = RSAKey.parse(certAndKeyGen.getSelfCertificate(x500Name, 365 * 24 * 60 * 60));
//        return new JwtCertificate(securityAuthorizationConfig.getIssuer(), jwk, keyPair);
//    }

    @Bean
    public static JwtCertificate jwkKey(SecurityAuthorizationConfig securityAuthorizationConfig)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, JOSEException,
            CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream stream = CertificateGeneratorConfig.class.getResourceAsStream("/jwk.jks")) {
            keyStore.load(stream, "jwkpass".toCharArray());
        }

        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("jwkalias");
        PublicKey publicKey = certificate.getPublicKey();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("jwkalias", "jwkpass".toCharArray());

        return new JwtCertificate(extractIssuer(certificate), JWK.parse(certificate),
                new KeyPair(publicKey, privateKey));
    }

    private static String extractIssuer(X509Certificate certificate) {
        X500Principal principal = certificate.getSubjectX500Principal();
        Pattern pattern = Pattern.compile("CN=([^,]+)");
        Matcher matcher = pattern.matcher(principal.getName());
        return matcher.find() ? matcher.group(1) : null;
    }
}

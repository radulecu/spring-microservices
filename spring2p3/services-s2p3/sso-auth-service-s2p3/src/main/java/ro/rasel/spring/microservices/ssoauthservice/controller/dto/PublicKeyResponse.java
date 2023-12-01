package ro.rasel.spring.microservices.ssoauthservice.controller.dto;

public class PublicKeyResponse {
    private final String base64X509Cert;

    public PublicKeyResponse(String base64X509Cert) {
        this.base64X509Cert = base64X509Cert;
    }

    public String getBase64X509Cert() {
        return base64X509Cert;
    }
}

package ro.rasel.spring.microservices.ssoauthservice.domain.exteptions;

public class EntityDoesNotExistsException extends Exception {
    public EntityDoesNotExistsException(String message) {
        super(message);
    }

    public EntityDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

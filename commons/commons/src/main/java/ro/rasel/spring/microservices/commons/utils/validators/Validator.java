package ro.rasel.spring.microservices.commons.utils.validators;

public interface Validator<O> {
    <T extends O> T validate(T o);
}

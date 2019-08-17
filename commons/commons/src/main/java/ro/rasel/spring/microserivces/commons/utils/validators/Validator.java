package ro.rasel.spring.microserivces.commons.utils.validators;

public interface Validator<O> {
    <T extends O> T validate(T o);
}

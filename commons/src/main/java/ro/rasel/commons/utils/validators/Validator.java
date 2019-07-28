package ro.rasel.commons.utils.validators;

public interface Validator<O> {
    <T extends O> T validate(T o);
}

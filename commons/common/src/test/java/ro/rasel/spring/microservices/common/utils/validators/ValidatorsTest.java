package ro.rasel.spring.microservices.common.utils.validators;

import org.hamcrest.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorsTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenObjectIsNull() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.notNullValidator("object").validate(null));

        MatcherAssert.assertThat(exception.getMessage(), is("object should not be null"));
    }

    @Test
    void shouldReturnObjectWhenObjectIsNotNull() {
        Number number = Validators.notNullValidator("object").validate(5);
        assertThat(number, is(5));

    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenStringIsBlank() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.notBlankValidator("stringObject").validate(""));

        MatcherAssert.assertThat(exception.getMessage(), is("stringObject should not be empty"));
    }

    @Test
    void shouldReturnCharSequenceWhenCharSequenceIsNotBlank() {
        String string = Validators.notBlankValidator("object").validate("valid");
        assertThat(string, is("valid"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenPatternIsInvalid() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validators.matchesPatternValidator("stringObject", "pattern").validate("invalid"));

        MatcherAssert.assertThat(exception.getMessage(), is("stringObject should match pattern"));
    }

    @Test
    void shouldReturnCharSequenceWhenPatternIsValid() {
        final String object = Validators.matchesPatternValidator("stringObject", "[a-z]*").validate("valid");
        assertThat(object, is("valid"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenObjectIsNullAndUsingOfComposedValidator() {
        final IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> Validator.of(Validators.notNullValidator("object"),
                        Validators.notBlankValidator("object"))
                        .validate(null));
        MatcherAssert.assertThat(exception1.getMessage(), is("object should not be null"));
        final IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> Validator.of(Validators.notBlankValidator("object"),
                        Validators.notNullValidator("object"))
                        .validate(null));
        MatcherAssert.assertThat(exception2.getMessage(), is("object should not be null"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenObjectIsEmptyAndUsingOfComposedChainedValidator() {
        final IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> Validator.of(Validators.notNullValidator("object"),
                        Validators.notBlankValidator("object"))
                        .validate(""));
        MatcherAssert.assertThat(exception1.getMessage(), is("object should not be empty"));
        final IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> Validator.of(Validators.notBlankValidator("object"),
                        Validators.notNullValidator("object"))
                        .validate(""));
        MatcherAssert.assertThat(exception2.getMessage(), is("object should not be empty"));
    }

    @Test
    void shouldReturnCharSequenceWhenCharSequenceIsNotNullAndNotEmptyAndWhenUsingOfComposedValidator() {
        final Validator<Object> objectValidator = Validators.notNullValidator("object");
        final Validator<CharSequence> charSequenceValidator = Validators.notBlankValidator("object");
        final Validator<CharSequence> and = Validator.of(objectValidator,charSequenceValidator);
        final String value = and.validate("value");
        assertThat(value, is("value"));

        final Validator<CharSequence> charSequenceValidator2 = Validators.notBlankValidator("object");
        final Validator<Object> objectValidator2 = Validators.notNullValidator("object");
        final Validator<CharSequence> and2 = Validator.of(objectValidator2,charSequenceValidator2);
        final String value2 = and2.validate("value");
        assertThat(value2, is("value"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenObjectIsNullAndUsingAndChainedValidator() {
        final IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> Validators.notNullValidator("object")
                        .and(Validators.notBlankValidator("object"))
                        .validate(null));
        MatcherAssert.assertThat(exception1.getMessage(), is("object should not be null"));
        final IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> Validators.notBlankValidator("object")
                        .and(Validators.notNullValidator("object"))
                        .validate(null));
        MatcherAssert.assertThat(exception2.getMessage(), is("object should not be null"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenObjectIsEmptyAndUsingAndChainedValidator() {
        final IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> Validators.notNullValidator("object")
                        .and(Validators.notBlankValidator("object"))
                        .validate(""));
        MatcherAssert.assertThat(exception1.getMessage(), is("object should not be empty"));
        final IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> Validators.notBlankValidator("object")
                        .and(Validators.notNullValidator("object"))
                        .validate(""));
        MatcherAssert.assertThat(exception2.getMessage(), is("object should not be empty"));
    }

    @Test
    void shouldReturnCharSequenceWhenCharSequenceIsNotNullAndNotEmptyAndWhenUsingAndChainedValidator() {
        final Validator<Object> objectValidator = Validators.notNullValidator("object");
        final Validator<CharSequence> charSequenceValidator = Validators.notBlankValidator("object");
        final Validator<CharSequence> and = objectValidator.and(charSequenceValidator);
        final String value = and.validate("value");
        assertThat(value, is("value"));

        final Validator<CharSequence> and2 = charSequenceValidator.and(objectValidator);
        final String value2 = and2.validate("value");
        assertThat(value2, is("value"));
    }
}
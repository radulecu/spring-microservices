package ro.rasel.spring.microservices.commons.utils.validators;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Validators {
    public static Validator<Object> notNullValidator(CharSequence label) {
        return new Validator<Object>() {
            @Override
            public <T> T validate(T t) {
                return returnOrThrowIllegatArgumentException(t, t == null, "should not be null", label);
            }
        };
    }

    public static Validator<CharSequence> notBlankValidator(CharSequence label) {
        return new Validator<CharSequence>() {
            @Override
            public <T extends CharSequence> T validate(T t) {
                return returnOrThrowIllegatArgumentException(t, t != null && t.toString().trim().isEmpty(),
                        "should not be empty", label);
            }
        };
    }

    public static Validator<CharSequence> matchesPatternValidator(CharSequence label, CharSequence pattern) {
        Pattern compile = Pattern.compile(pattern.toString());
        return new Validator<CharSequence>() {
            @Override
            public <T extends CharSequence> T validate(T t) {
                final String message = "should match " + pattern;
                return returnOrThrowIllegatArgumentException(t, t != null && !compile.matcher(t).matches(), message,
                        label);
            }
        };
    }

    private static <T> T returnOrThrowIllegatArgumentException(
            T t, boolean throwException, String message, CharSequence label) {
        if (throwException) {
            throw new IllegalArgumentException(label + " " + message);
        }

        return t;
    }

    @SafeVarargs
    public static <O> Validator<O> of(
            String label, Function<String, Validator<? super O>> validator,
            Function<String, Validator<? super O>>... validators) {
        return Validator.of(Stream.of(Stream.of(validator), Arrays.stream(validators))
                .flatMap(Function.identity())
                .map(f -> f.apply(label))
                .collect(Collectors.toList()));
    }
}

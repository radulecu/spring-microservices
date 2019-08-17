package ro.rasel.spring.microserivces.commons.utils.validators;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validators {
    public static Validator<CharSequence> notBlankValidator(CharSequence label) {
        return new Validator<CharSequence>() {
            @Override
            public <T extends CharSequence> T validate(T t) {
                Objects.requireNonNull(t, label + " argument should not be null");
                if (t.toString().trim().isEmpty()) {
                    throw new IllegalArgumentException(label + " should not be empty");
                }
                return t;
            }
        };
    }

    public static Validator<CharSequence> matchesPatternValidator(CharSequence label, CharSequence pattern) {
        Pattern compile = Pattern.compile(pattern.toString());
        return new Validator<CharSequence>() {
            @Override
            public <T extends CharSequence> T validate(T t) {
                Objects.requireNonNull(t, label + " argument should not be null");
                if (!compile.matcher(t).matches()) {
                    throw new IllegalArgumentException(label + " should match " + pattern);
                }
                return t;
            }
        };
    }
}

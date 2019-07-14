package ro.rasel.commons.utils.validators;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validators {
    public static Validator<CharSequence> notBlankValidateor(CharSequence label) {
        return cs -> {
            Objects.requireNonNull(cs, label + " argument should not be null");
            if (cs.toString().trim().isEmpty()) {
                throw new IllegalArgumentException(label + " should not be empty");
            }
        };
    }

    public static Validator<CharSequence> matchesPatternValidator(CharSequence label, CharSequence pattern) {
        Pattern compile = Pattern.compile(pattern.toString());
        return cs -> {
            Objects.requireNonNull(cs, label + " argument should not be null");
            if (!compile.matcher(cs).matches()) {
                throw new IllegalArgumentException(label + " should match " + pattern);
            }
        };
    }
}

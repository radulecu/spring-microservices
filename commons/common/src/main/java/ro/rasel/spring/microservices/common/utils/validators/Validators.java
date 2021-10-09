package ro.rasel.spring.microservices.common.utils.validators;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
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

    public static <E> Validator<Collection<E>> notEmptyCollectionValidator(CharSequence label) {
        return new Validator<Collection<E>>() {
            @Override
            public <T extends Collection<E>> T validate(T t) {
                return returnOrThrowIllegatArgumentException(t, t != null && t.isEmpty(),
                        "should not be empty", label);
            }
        };
    }

    public static <K,V> Validator<Map<K,V>> notEmptyMapValidator(CharSequence label) {
        return new Validator<Map<K,V>>() {
            @Override
            public <T extends Map<K,V>> T validate(T t) {
                return returnOrThrowIllegatArgumentException(t, t != null && t.isEmpty(),
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
//
//    @SafeVarargs
//    public static <O> Validator<O> of(Validator<? super O> validator, Validator<? super O>... validators) {
//        return Validator.of(Stream.of(Stream.of(validator), Arrays.stream(validators))
//                .flatMap(Function.identity())
//                .collect(Collectors.toList()));
//    }
//
//    public <T> ValidatorBuilder<T> builder(String label) {
//        new ValidatorBuilder<>(label);
//    }
//
//    public static class ValidatorBuilder<T> {
//        private final String label;
//        private final Validator<T> validator;
//
//        public ValidatorBuilder(String label) {
//            this.label = label;
//            validator = null;
//        }
//
//        public ValidatorBuilder(String label, Validator<T> validator) {
//            this.label = label;
//            this.validator = validator;
//        }
//
//        public ValidatorBuilder<T> notNull() {
//            return validator(Validators::notNullValidator);
//        }
//
//        public ValidatorBuilder<T> notBlank() {
//            return validator(Validators::notBlankValidator);
//        }
//
//        public <E extends T & CharSequence> ValidatorBuilder<E> matchesPattern(E pattern) {
//            Function<String,Validator<CharSequence>> validatorFunction =
//                    label -> Validators.matchesPatternValidator(label, pattern);
//            final Validator<? super E> apply = validatorFunction.apply(this.label);
//            final Validator<E> eValidator = this.validator == null ? apply : Validators.of(this.validator, apply);
//            return new ValidatorBuilder<E>(this.label, eValidator);
//        }
//
//        public <E extends T> ValidatorBuilder<E> validator(Function<String, Validator<? super E>> validatorFunction) {
//            final Validator<? super E> apply = validatorFunction.apply(this.label);
//            final Validator<E> eValidator = this.validator == null ? apply : Validators.of(this.validator, apply);
//            return new ValidatorBuilder<E>(this.label, eValidator);
//        }
//
//        public Validator<T> build() {
//            return validator;
//        }
//    }
}

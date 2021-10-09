package ro.rasel.spring.microservices.common.utils.validators;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Validator<O> {
    <T extends O> T validate(T o);

    default <T extends O> Validator<T> and(Validator<? super T> validator) {
        return of(this, validator);
    }

    @SafeVarargs
    static <O> Validator<O> of(Validator<? super O> validator, Validator<? super O>... validators) {
        final List<Validator<? super O>> validators1 =
                Stream.of(Stream.of(validator), Arrays.stream(validators))
                        .flatMap(stream -> stream)
                        .collect(Collectors.toList());
        return of(validators1);
    }

    static <O> Validator<O> of(Collection<Validator<? super O>> validators) {
        if (validators == null || validators.size() == 0) {
            return of(Validator.empty());
        }

        return new Validator<O>() {
            @Override
            public <T extends O> T validate(T o) {
                for (Validator<? super O> validator : validators) {
                    validator.validate(o);
                }
                return o;
            }
        };
    }

    static Validator<Object> empty() {
        return new Validator<Object>() {
            @Override
            public <T> T validate(T o) {
                return o;
            }
        };
    }
}

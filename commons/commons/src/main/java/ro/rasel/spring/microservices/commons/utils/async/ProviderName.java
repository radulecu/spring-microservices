package ro.rasel.spring.microservices.commons.utils.async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProviderName {
    String ALL = "all";

    String[] value() default {ALL};
}

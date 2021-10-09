package ro.rasel.spring.microservices.common.utils.async;

public interface AsynchronousDataProvider<V> {
    V extract();

    boolean setup(V value);

    default void clean() {
        // do nothing
    }
}

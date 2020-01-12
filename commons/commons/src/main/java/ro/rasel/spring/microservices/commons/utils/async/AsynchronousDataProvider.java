package ro.rasel.spring.microservices.commons.utils.async;

public interface AsynchronousDataProvider<V> {
    V extract();

    void setup(V value);
}

package ro.rasel.spring.microserivces.commons.utils.async;

public interface AsynchronousDataProvider<V> {
    V extract();

    void setup(V value);
}

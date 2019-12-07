package ro.rasel.spring.microserivces.commons.utils;

import java.util.Objects;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class Touple<P, Q> {
    private final P p;
    private final Q q;

    public Touple(P p, Q q) {
        this.p = p;
        this.q = q;
    }

    public P getP() {
        return p;
    }

    public Q getQ() {
        return q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Touple<?, ?> touple = (Touple<?, ?>) o;
        return Objects.equals(p, touple.p) &&
                Objects.equals(q, touple.q);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, q);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Touple.class.getSimpleName() + "[", "]")
                .add("p=" + p)
                .add("q=" + q)
                .toString();
    }
}
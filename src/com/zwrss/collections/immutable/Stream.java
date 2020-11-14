package com.zwrss.collections.immutable;

import com.zwrss.collections.immutable.stream.Calculated;
import com.zwrss.collections.immutable.stream.Empty;

import java.util.function.Supplier;

/**
 * Simple Stream. Educational means only. It sure is not safe to use.
 * @param <T> type of elements
 */
public abstract class Stream<T> implements Iterable<T> {

    abstract public T head();

    abstract public Stream<T> tail();

    static private <X> Stream<X> apply(int index, X[] elements) {
        if (index >= elements.length) return (Stream<X>) Empty.getInstance();
        Stream<X> inner = apply(index + 1, elements);
        return new Calculated<X>(elements[index], () -> apply(index + 1, elements));
    }

    static public <X> Stream<X> apply(X... elements) {
        return apply(0, elements);
    }

    static public <X> Stream<X> empty() {
        return (Stream<X>) Empty.getInstance();
    }

    static public <X> Stream<X> apply(Supplier<X> supplier) {
        X element = supplier.get();
        if (element == null) return (Stream<X>) empty();
        else return new Calculated<X>(element, () -> (Stream<X>) apply(supplier));
    }


}

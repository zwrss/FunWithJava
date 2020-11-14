package com.zwrss.collections;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Iterable<T> {

    void foreach(Consumer<T> callback);

    T find(Predicate<T> predicate);

    boolean isEmpty();

    default boolean isNonEmpty() {
        return !isEmpty();
    }

}

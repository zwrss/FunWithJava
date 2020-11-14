package com.zwrss.collections;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Iterable<T> {

    public void foreach(Consumer<T> callback);

    public T find(Predicate<T> predicate);

    public int size();

}

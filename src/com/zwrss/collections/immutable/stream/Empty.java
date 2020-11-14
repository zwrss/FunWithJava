package com.zwrss.collections.immutable.stream;

import com.zwrss.collections.immutable.Iterable;
import com.zwrss.collections.immutable.Stream;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Empty extends Stream<Object> {
    private Empty() {
    }

    @Override
    public Object head() {
        throw new NoSuchElementException("head() on empty stream!");
    }

    @Override
    public Stream<Object> tail() {
        return this;
    }

    @Override
    public <V> Stream<V> map(Class<V> clazz, Function<Object, V> callback) {
        return (Stream<V>) this;
    }

    @Override
    public void foreach(Consumer<Object> callback) {

    }

    @Override
    public Object find(Predicate<Object> predicate) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    private static Empty instance;

    public static Empty getInstance() {
        if (instance == null) instance = new Empty();
        return instance;
    }
}

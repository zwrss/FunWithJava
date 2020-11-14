package com.zwrss.collections.immutable.stream;

import com.zwrss.collections.immutable.Iterable;
import com.zwrss.collections.immutable.Stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Calculated<T> extends Stream<T> {

    private T element;
    private Supplier<Stream<T>> tailSupplier;

    public Calculated(T element, Supplier<Stream<T>> tailSupplier) {
        this.tailSupplier = tailSupplier;
        this.element = element;
    }

    @Override
    public T head() {
        return element;
    }

    @Override
    public Stream<T> tail() {
        return this.tailSupplier.get();
    }

    @Override
    public <V> Iterable<V> map(Class<V> clazz, Function<T, V> callback) {
        return new Calculated<V>(callback.apply(element), () -> (Stream<V>) tailSupplier.get().map(clazz, callback));
    }

    @Override
    public void foreach(Consumer<T> callback) {
        callback.accept(element);
        tail().foreach(callback);
    }

    @Override
    public T find(Predicate<T> predicate) {
        if (predicate.test(element)) return element;
        else return tail().find(predicate);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
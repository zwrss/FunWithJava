package com.zwrss.collections.immutable;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Array<T> implements Iterable<T> {

    private final T[] elements;

    public Array(T... elements) {
        this.elements = elements.clone();
    }

    @Override
    public void foreach(Consumer<T> callback) {
        for (T element : elements) {
            callback.accept(element);
        }
    }

    @Override
    public T find(Predicate<T> predicate) {
        for (T e : elements) {
            if (predicate.test(e)) return e;
        }
        return null;
    }

    @Override
    public <V> Array<V> map(Class<V> clazz, Function<T, V> callback) {
        V[] mappedElements = (V[]) java.lang.reflect.Array.newInstance(clazz, elements.length);
        for (int i = 0; i < elements.length; i++) {
            mappedElements[i] = callback.apply(elements[i]);
        }
        return new Array<V>(mappedElements);
    }

    @Override
    public Array<T> add(T element) {
        T[] newElements = Arrays.copyOf(elements, elements.length + 1);
        newElements[newElements.length - 1] = element;
        return new Array<T>(newElements);
    }

    @Override
    public int size() {
        return elements.length;
    }
}

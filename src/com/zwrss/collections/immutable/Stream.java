package com.zwrss.collections.immutable;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Simple Stream. Educational means only. It sure is not safe to use.
 *
 * @param <T> type of elements
 */
public abstract class Stream<T> implements Iterable<T> {

    abstract public T head();

    abstract public Stream<T> tail();

    private static class Calculated<T> extends Stream<T> {

        private final T element;
        private final Supplier<Stream<T>> tailSupplier;

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
        public <V> Stream<V> map(Class<V> clazz, Function<T, V> callback) {
            return new Calculated<>(callback.apply(element), () -> tailSupplier.get().map(clazz, callback));
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

    private static class Empty extends Stream<Object> {

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

    }

    private static class Merged<T> extends Stream<T> {

        private final Stream<T> left;
        private final Stream<T> right;

        public Merged(Stream<T> left, Stream<T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public T head() {
            return left.head();
        }

        @Override
        public Stream<T> tail() {
            Stream<T> leftTail = left.tail();
            if (leftTail.isEmpty()) return right;
            else return new Merged<>(leftTail, right);
        }

        @Override
        public <V> Stream<V> map(Class<V> clazz, Function<T, V> callback) {
            return new Merged<>(left.map(clazz, callback), right.map(clazz, callback));
        }

        @Override
        public void foreach(Consumer<T> callback) {
            left.foreach(callback);
            right.foreach(callback);
        }

        @Override
        public T find(Predicate<T> predicate) {
            T result = left.find(predicate);
            if (result == null) result = right.find(predicate);
            return result;
        }

        @Override
        public boolean isEmpty() {
            return left.isEmpty() && right.isEmpty();
        }

        @Override
        public boolean isNonEmpty() {
            return left.isNonEmpty() || right.isNonEmpty();
        }
    }

    // additional methods

    @Override
    public abstract <V> Stream<V> map(Class<V> clazz, Function<T, V> callback);

    public Stream<T> add(Stream<T> that) {
        if (this.isEmpty()) return that;
        else if (that.isEmpty()) return this;
        else return new Merged<>(this, that);
    }

    // statics

    private static <X> Stream<X> apply(int index, X[] elements) {
        return index >= elements.length ? empty() : new Calculated<>(elements[index], () -> apply(index + 1, elements));
    }

    public static <X> Stream<X> apply(X... elements) {
        return apply(0, elements);
    }

    private static Empty empty;

    public static <X> Stream<X> empty() {
        if (empty == null) empty = new Empty();
        return (Stream<X>) empty;
    }

    public static <X> Stream<X> apply(Supplier<X> supplier) {
        X element = supplier.get();
        if (element == null) return empty();
        else return new Calculated<>(element, () -> apply(supplier));
    }

}

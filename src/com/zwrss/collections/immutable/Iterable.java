package com.zwrss.collections.immutable;

import java.util.function.Function;

public interface Iterable<T> extends com.zwrss.collections.Iterable<T> {

    public <V> Iterable<V> map(Class<V> clazz, Function<T, V> callback);

}

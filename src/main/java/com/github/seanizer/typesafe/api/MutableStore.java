package com.github.seanizer.typesafe.api;

import java.util.function.Supplier;

public interface MutableStore extends Store {

    <T> void put(Key<T> key, T value);

    default <T> T getOrCalculate(Key<T> key, Supplier<T> valueSupplier) {
        return get(key).orElseGet(() -> {
            T value = valueSupplier.get();
            put(key, value);
            return value;
        });
    }

    <T> void remove(Key<T> key);



}

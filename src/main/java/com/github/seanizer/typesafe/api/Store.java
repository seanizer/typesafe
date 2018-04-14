package com.github.seanizer.typesafe.api;

import java.util.Optional;

public interface Store {

    <T> Optional<T> get(Key<T> key);

    default <T> T getOrDefault(Key<T> key, T defaultValue) {
        return get(key).orElse(defaultValue);
    }

    default <T> boolean containsKey(Key<T> key) {
        return get(key).isPresent();
    }


}

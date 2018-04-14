package com.github.seanizer.typesafe.map;

import com.github.seanizer.typesafe.api.Key;
import com.github.seanizer.typesafe.api.MutableStore;
import java.util.Map;

public class MutableMapStore extends MapStore implements MutableStore {

    public MutableMapStore(final Map<String, Object> map) {
        super(map);
    }

    @Override
    public <T> void put(final Key<T> key, final T value) {
        key.transform(value).ifPresent(t -> map.put(key.name(),t));
    }

    @Override
    public <T> void remove(final Key<T> key) {
        key.transform(map.get(key.name())).ifPresent(t -> map.remove(key.name()));
    }
}

package com.github.seanizer.typesafe.map;

import java.util.Map;
import java.util.Optional;
import com.github.seanizer.typesafe.api.Key;
import com.github.seanizer.typesafe.api.Store;

public class MapStore implements Store {

    protected final Map<String, Object> map;

    public MapStore(final Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public <T> Optional<T> get(final Key<T> key) {
        return key.transform(map.get(key.name()));
    }
}

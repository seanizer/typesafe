package com.github.seanizer.typesafe.servlet;

import javax.servlet.http.HttpServletRequest;
import com.github.seanizer.typesafe.api.Key;
import com.github.seanizer.typesafe.api.MutableStore;

public class MutableHttpServletRequestStore extends HttpServletRequestStore implements MutableStore {

    public MutableHttpServletRequestStore(final HttpServletRequest request) {
        super(request);
    }

    @Override
    public <T> void put(final Key<T> key, final T value) {
        key.transform(value).ifPresent(v -> request.setAttribute(key.name(), v));
    }

    @Override
    public <T> void remove(final Key<T> key) {
        String name = key.name();
        key.transform(request.getAttribute(name)).ifPresent(v->request.removeAttribute(name));
    }
}

package com.github.seanizer.typesafe.servlet;


import javax.servlet.http.HttpSession;
import com.github.seanizer.typesafe.api.Key;
import com.github.seanizer.typesafe.api.MutableStore;

public class MutableHttpSessionStore extends HttpSessionStore implements MutableStore {

    public MutableHttpSessionStore(final HttpSession session) {
        super(session);
    }

    @Override
    public <T> void put(final Key<T> key, final T value) {
        key.transform(value).ifPresent(v -> session.setAttribute(key.name(), v));
    }

    @Override
    public <T> void remove(final Key<T> key) {
        String name = key.name();
        key.transform(session.getAttribute(name)).ifPresent(v -> session.removeAttribute(name));
    }
}

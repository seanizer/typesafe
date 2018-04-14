package com.github.seanizer.typesafe.servlet;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import com.github.seanizer.typesafe.api.Key;
import com.github.seanizer.typesafe.api.Store;

public class HttpSessionStore implements Store {

    protected final HttpSession session;

    public HttpSessionStore(final HttpSession session) {
        this.session = session;
    }

    @Override
    public <T> Optional<T> get(final Key<T> key) {
        return key.transform(session.getAttribute(key.name()));
    }
}

package com.github.seanizer.typesafe.servlet;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import com.github.seanizer.typesafe.api.Key;
import com.github.seanizer.typesafe.api.Store;

public class HttpServletRequestStore implements Store {

    protected final HttpServletRequest request;

    public HttpServletRequestStore(final HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public <T> Optional<T> get(final Key<T> key) {
        return key.transform(request.getAttribute(key.name()));
    }
}

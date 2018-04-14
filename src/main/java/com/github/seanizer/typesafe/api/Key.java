package com.github.seanizer.typesafe.api;


import java.util.Optional;
import javax.annotation.Nullable;

public interface Key<T> {

    String name();
    Optional<T> transform(@Nullable Object candidate);

}

package com.github.seanizer.typesafe.util;

import com.github.seanizer.typesafe.api.Key;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class Keys {

    static abstract class AbstractKey<T> implements Key<T> {

        private final String name;

        AbstractKey(final String name) {
            this.name = name;
        }

        @Override
        public final String name() {
            return name;
        }

    }

    public static <T> Key<T> forClass(Class<T> type, String name) {
        return new AbstractKey<T>(name) {
            @Override
            public Optional<T> transform(final Object candidate) {
                return Optional.ofNullable(candidate).filter(type::isInstance).map(type::cast);
            }

            @Override
            public String toString() {
                return String.format("Keys.forClass(%s, %s)", type.getName(), name);
            }
        };
    }

    public static Key<String> stringKey(String name){
        return new AbstractKey<String>(name) {
            @Override
            public Optional<String> transform(@Nullable final Object candidate) {
                return Optional.ofNullable(candidate).filter(String.class::isInstance).map(String.class::cast);
            }

            @Override
            public String toString() {
                return String.format("Keys.stringKey(%s)", name);
            }
        };
    }

    public static <T> Key<List<T>> uncheckedListKey(Class<T> elementType, String name) {
        return new AbstractKey<List<T>>(name) {
            @Override
            public Optional<List<T>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<List<T>> listClass = (Class) List.class;
                return Optional.ofNullable(candidate).filter(listClass::isInstance).map(listClass::cast);
            }

            @Override
            public String toString() {
                return String.format("Keys.uncheckedListKey(%s, %s)", elementType.getName(), name);
            }
        };
    }

    public static <T> Key<List<T>> strictListKey(Class<T> elementType, String name) {
        return new AbstractKey<List<T>>(name) {
            @Override
            public Optional<List<T>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<List<T>> listClass = (Class) List.class;
                return Optional.ofNullable(candidate)
                               .filter(listClass::isInstance)
                               .map(listClass::cast)
                               .filter(list -> list.stream().allMatch(elementType::isInstance));
            }

            @Override
            public String toString() {
                return String.format("Keys.strictListKey(%s, %s)", elementType.getName(), name);
            }
        };
    }

    public static <T> Key<List<T>> filteringListKey(Class<T> elementType, String name) {
        return new AbstractKey<List<T>>(name) {
            @Override
            public Optional<List<T>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<List<T>> listClass = (Class) List.class;
                return Optional.ofNullable(candidate)
                               .filter(listClass::isInstance)
                               .map(listClass::cast)
                               .map(list -> list.stream()
                                                .filter(elementType::isInstance)
                                                .collect(Collectors.toList())
                               );
            }

            @Override
            public String toString() {
                return String.format("Keys.filteringListKey(%s, %s)", elementType.getName(), name);
            }
        };
    }

    public static <T> Key<Set<T>> uncheckedSetKey(Class<T> elementType, String name) {
        return new AbstractKey<Set<T>>(name) {
            @Override
            public Optional<Set<T>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<Set<T>> setClass = (Class) Set.class;
                return Optional.ofNullable(candidate).filter(setClass::isInstance).map(setClass::cast);
            }

            @Override
            public String toString() {
                return String.format("Keys.uncheckedSetKey(%s, %s)", elementType.getName(), name);
            }
        };
    }

    public static <T> Key<Set<T>> strictSetKey(Class<T> elementType, String name) {
        return new AbstractKey<Set<T>>(name) {
            @Override
            public Optional<Set<T>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<Set<T>> setClass = (Class) Set.class;
                return Optional.ofNullable(candidate)
                               .filter(setClass::isInstance)
                               .map(setClass::cast)
                               .filter(Set -> Set.stream().allMatch(elementType::isInstance));
            }

            @Override
            public String toString() {
                return String.format("Keys.strictSetKey(%s, %s)", elementType.getName(), name);
            }
        };
    }

    public static <T> Key<Set<T>> filteringSetKey(Class<T> elementType, String name) {
        return new AbstractKey<Set<T>>(name) {
            @Override
            public Optional<Set<T>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<Set<T>> setClass = (Class) Set.class;
                return Optional.ofNullable(candidate)
                               .filter(setClass::isInstance)
                               .map(setClass::cast)
                               .map(Set -> Set.stream()
                                              .filter(elementType::isInstance)
                                              .collect(Collectors.toSet())
                               );
            }

            @Override
            public String toString() {
                return String.format("Keys.filteringSetKey(%s, %s)", elementType.getName(), name);
            }
        };
    }

    public static <K, V> Key<Map<K, V>> uncheckedMapKey(Class<K> keyType, Class<V> valueType, String name) {
        return new AbstractKey<Map<K, V>>(name) {
            @Override
            public Optional<Map<K, V>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<Map<K, V>> MapClass = (Class) Map.class;
                return Optional.ofNullable(candidate).filter(MapClass::isInstance).map(MapClass::cast);
            }

            @Override
            public String toString() {
                return String.format("Keys.uncheckedMapKey(%s, %s, %s)", keyType.getName(), valueType.getName(), name);
            }
        };
    }

    public static <K, V> Key<Map<K, V>> strictMapKey(Class<K> keyType, Class<V> valueType, String name) {
        return new AbstractKey<Map<K, V>>(name) {
            @Override
            public Optional<Map<K, V>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<Map<K, V>> MapClass = (Class) Map.class;
                return Optional.ofNullable(candidate)
                               .filter(MapClass::isInstance)
                               .map(MapClass::cast)
                               .filter(map -> map.entrySet()
                                                 .stream()
                                                 .allMatch(entry -> keyType.isInstance(entry.getKey())
                                                     && valueType.isInstance(entry.getValue())));
            }

            @Override
            public String toString() {
                return String.format("Keys.strictMapKey(%s, %s, %s)", keyType.getName(), valueType.getName(), name);
            }
        };
    }

    public static <K, V> Key<Map<K, V>> filteringMapKey(Class<K> keyType, Class<V> valueType, String name) {
        return new AbstractKey<Map<K, V>>(name) {
            @Override
            public Optional<Map<K, V>> transform(@Nullable final Object candidate) {
                @SuppressWarnings("unchecked") Class<Map<K, V>> MapClass = (Class) Map.class;
                return Optional.ofNullable(candidate)
                               .filter(MapClass::isInstance)
                               .map(MapClass::cast)
                               .map(Map -> Map.entrySet().stream()
                                              .filter(e -> keyType.isInstance(e.getKey())
                                                  && valueType.isInstance(e.getValue()))
                                              .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
                               );
            }

            @Override
            public String toString() {
                return String.format("Keys.filteringMapKey(%s, %s, %s)", keyType.getName(), valueType.getName(), name);
            }
        };
    }


}

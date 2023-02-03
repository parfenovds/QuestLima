package com.javarush.parfenov.repository;

import java.util.Collection;
import java.util.Optional;

public interface TwoPrimaryKeyRepository<T> {
    T create(T entity);
    Collection<T> getAll();
    Optional<T> get(Long nodeId, Long questId);
    boolean update(T entity);
    boolean delete(Long nodeId, Long questId);
    boolean delete(T entity);


}

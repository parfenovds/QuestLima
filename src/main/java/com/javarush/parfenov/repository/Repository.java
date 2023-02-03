package com.javarush.parfenov.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {
    T create(T entity);
    Collection<T> getAll();
    Optional<T> get(Long id);
    boolean update(T entity);
    boolean delete(Long id);
    boolean delete(T entity);


}

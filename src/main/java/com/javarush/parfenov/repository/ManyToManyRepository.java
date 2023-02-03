package com.javarush.parfenov.repository;

import java.util.Collection;
import java.util.Optional;

public interface ManyToManyRepository<T> {
    T create(T entity);
    Collection<T> getAll();
    Collection<T> getApproptiate(Long parentId);
    Optional<T> get(Long parentId, Long childId);
    boolean update(T entity);
    boolean delete(Long parentId, Long childId);
    boolean delete(T entity);


}

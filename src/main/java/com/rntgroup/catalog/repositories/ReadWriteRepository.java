package com.rntgroup.catalog.repositories;

public interface ReadWriteRepository<T, ID> extends ReadOnlyRepository<T, ID> {

  T save(T entity);

  void deleteById(ID id);
}

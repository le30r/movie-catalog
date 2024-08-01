package com.rntgroup.catalog.data.repositories;

public interface ReadWriteRepository<T, ID> extends ReadOnlyRepository<T, ID> {

  T save(T entity);

  void deleteById(ID id);
}

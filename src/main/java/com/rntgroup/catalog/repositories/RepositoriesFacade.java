package com.rntgroup.catalog.repositories;

public interface RepositoriesFacade<T, ID>
    extends ReadOnlyRepository<T, ID>, ReadWriteRepository<T, ID> {

}

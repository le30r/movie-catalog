package com.rntgroup.catalog.data.repositories;

public interface RepositoriesFacade<T, ID>
    extends ReadOnlyRepository<T, ID>, ReadWriteRepository<T, ID> {

}

package com.rntgroup.catalog.repositories.impl;

import com.rntgroup.catalog.configurations.RepositoriesConfig;
import com.rntgroup.catalog.repositories.ReadOnlyRepository;
import com.rntgroup.catalog.repositories.csv.CsvRepository;
import com.rntgroup.catalog.repositories.csv.FileReaderWriterFactory;
import com.rntgroup.catalog.repositories.entity.MovieRecord;
import com.rntgroup.catalog.repositories.exceptions.DataProcessingException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieInternalRepository extends CsvRepository<MovieRecord> implements
    ReadOnlyRepository<MovieRecord, Integer> {

  private final RepositoriesConfig config;

  @Autowired
  public MovieInternalRepository(FileReaderWriterFactory fileReaderWriterFactory, RepositoriesConfig config) {
    super(fileReaderWriterFactory);
    this.config = config;
  }


  @Override
  public List<MovieRecord> findAll() {
    return readMovies();
  }

  @Override
  public Optional<MovieRecord> findById(Integer id) {
    return readMovies()
        .stream()
        .filter(record -> Objects.equals(record.getId(), id))
        .findFirst();
  }

  private List<MovieRecord> readMovies() {
    try {
      return readAll(config.getInternal(),
          MovieRecord.class);
    } catch (IOException e) {
      throw new DataProcessingException(e);
    }
  }
}

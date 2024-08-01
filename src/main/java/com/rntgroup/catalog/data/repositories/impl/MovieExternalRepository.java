package com.rntgroup.catalog.data.repositories.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rntgroup.catalog.configurations.RepositoriesConfig;
import com.rntgroup.catalog.data.repositories.csv.FileReaderWriterFactory;
import com.rntgroup.catalog.data.repositories.ReadWriteRepository;
import com.rntgroup.catalog.data.repositories.csv.CsvRepository;
import com.rntgroup.catalog.data.entity.MovieRecord;
import com.rntgroup.catalog.data.exceptions.DataProcessingException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieExternalRepository extends CsvRepository<MovieRecord>
    implements ReadWriteRepository<MovieRecord, Integer> {

  private final RepositoriesConfig config;

  @Autowired
  public MovieExternalRepository(
      FileReaderWriterFactory fileReaderFactory, RepositoriesConfig repositoriesConfig) {
    super(fileReaderFactory);
    config = repositoriesConfig;
  }

  @Override
  public MovieRecord save(MovieRecord movie) {
    List<MovieRecord> movies = readMovies();
    boolean updated = false;

    if (movie.getId() == null) {
      int newId = movies.stream().mapToInt(MovieRecord::getId).max().orElse(0) + 1;
      movie.setId(newId);
      movies.add(movie);
    } else {
      for (int i = 0; i < movies.size(); i++) {
        if (movies.get(i).getId().equals(movie.getId())) {
          movies.set(i, movie);
          updated = true;
          break;
        }
      }

      if (!updated) {
        movies.add(movie);
      }
    }

    writeMovies(movies);

    return movie;
  }


  @Override
  public void deleteById(Integer id) {
    List<MovieRecord> movies = readMovies();
    movies.removeIf(movie -> movie.getId().equals(id));
    writeMovies(movies);
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

  private void writeMovies(List<MovieRecord> movies) {
    try {
      writeAll(movies, config.getExternal());
    } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
      throw new DataProcessingException(e);
    }
  }

  private List<MovieRecord> readMovies() {
    try {
      return readAll(config.getExternal(), MovieRecord.class);
    } catch (IOException e) {
      throw new DataProcessingException(e);
    }
  }

}

package com.rntgroup.catalog.data.repositories.impl;

import com.rntgroup.catalog.data.repositories.RepositoriesFacade;
import com.rntgroup.catalog.data.entity.MovieRecord;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MovieRepositoryFacade implements RepositoriesFacade<MovieRecord, Integer> {

  private final MovieExternalRepository externalRepository;
  private final MovieInternalRepository internalRepository;

  @Override
  public MovieRecord save(MovieRecord entity) {
    return externalRepository.save(entity);
  }

  @Override
  public void deleteById(Integer integer) {
    externalRepository.deleteById(integer);
  }

  @Override
  public List<MovieRecord> findAll() {
    var internalMovies = internalRepository.findAll();
    var externalMovies = externalRepository.findAll();
    LinkedList<MovieRecord> result = new LinkedList<>(internalMovies);
    result.addAll(externalMovies);
    return result;
  }

  @Override
  public Optional<MovieRecord> findById(Integer integer) {
    var internalMovie = internalRepository.findById(integer);
    if (internalMovie.isPresent()) {
      return internalMovie;
    }
    return externalRepository.findById(integer);

  }
}

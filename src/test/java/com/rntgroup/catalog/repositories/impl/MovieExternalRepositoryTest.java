package com.rntgroup.catalog.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.rntgroup.catalog.configurations.RepositoriesConfig;
import com.rntgroup.catalog.repositories.csv.FileReaderWriterFactory;
import com.rntgroup.catalog.repositories.entity.MovieRecord;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieExternalRepositoryTest {

  @Spy
  private FileReaderWriterFactory fileReaderWriterFactory = new FileReaderWriterFactory();

  @Mock
  private RepositoriesConfig config;

  @InjectMocks
  private MovieExternalRepository repository;

  private Path tempFile;

  @BeforeEach
  void setUp() throws IOException {
    tempFile = Files.createTempFile("test-movies", ".csv");
    InputStream resourceAsStream =
        getClass().getClassLoader().getResourceAsStream("test-movies.csv");
    Files.copy(resourceAsStream,
        tempFile, StandardCopyOption.REPLACE_EXISTING);

    when(config.getExternal()).thenReturn(tempFile.toString());
  }

  @Test
  void testSaveNewMovie() {
    MovieRecord newMovie = new MovieRecord(null, "New Movie", "New Director", "NewActor1;NewActor2", "NewGenre1;NewGenre2");
    List<MovieRecord> moviesBefore = repository.findAll();

    MovieRecord savedMovie = repository.save(newMovie);
    List<MovieRecord> moviesAfter = repository.findAll();

    assertNotNull(savedMovie.getId());
    assertEquals(moviesBefore.size() + 1, moviesAfter.size());
    assertTrue(moviesAfter.contains(savedMovie));
  }

  @Test
  void testSaveUpdateMovie() {
    MovieRecord updatedMovie = new MovieRecord(1, "Updated Movie", "Updated Director", "UpdatedActor1;UpdatedActor2", "UpdatedGenre1;UpdatedGenre2");
    MovieRecord savedMovie = repository.save(updatedMovie);

    assertEquals(updatedMovie.getId(), savedMovie.getId());
    assertEquals(updatedMovie.getName(), savedMovie.getName());

    Optional<MovieRecord> foundMovie = repository.findById(1);
    assertTrue(foundMovie.isPresent());
    assertEquals(updatedMovie.getName(), foundMovie.get().getName());
  }

  @Test
  void testDeleteById() {
    List<MovieRecord> moviesBefore = repository.findAll();
    repository.deleteById(1);
    List<MovieRecord> moviesAfter = repository.findAll();

    assertEquals(moviesBefore.size() - 1, moviesAfter.size());
    assertFalse(moviesAfter.stream().anyMatch(movie -> movie.getId() == 1));
  }

  @Test
  void testFindAll() {
    List<MovieRecord> movies = repository.findAll();
    assertEquals(2, movies.size());
  }

  @Test
  void testFindById() {
    Optional<MovieRecord> foundMovie = repository.findById(1);
    assertTrue(foundMovie.isPresent());
    assertEquals(1, foundMovie.get().getId());
    assertEquals("Movie One", foundMovie.get().getName());
    assertEquals("Director One", foundMovie.get().getDirector());

    foundMovie = repository.findById(2);
    assertTrue(foundMovie.isPresent());
    assertEquals(2, foundMovie.get().getId());
    assertEquals("Movie Two", foundMovie.get().getName());
    assertEquals("Director Two", foundMovie.get().getDirector());
  }
}

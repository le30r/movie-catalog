package com.rntgroup.catalog.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.rntgroup.catalog.configurations.RepositoriesConfig;
import com.rntgroup.catalog.repositories.csv.FileReaderWriterFactory;
import com.rntgroup.catalog.repositories.entity.MovieRecord;
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
public class MovieInternalRepositoryTest {

  @Spy
  private FileReaderWriterFactory fileReaderWriterFactory;

  @Mock
  private RepositoriesConfig config;

  @InjectMocks
  private MovieInternalRepository repository;


  @BeforeEach
  void setUp() {
    when(config.getInternal()).thenReturn("internal.csv");
  }

  @Test
  void testFindAll() {
    List<MovieRecord> movies = repository.findAll();
    assertNotNull(movies);
    assertEquals(2, movies.size());
  }

  @Test
  void testFindById() {
    Optional<MovieRecord> movie = repository.findById(null);
    assertTrue(movie.isPresent());
    assertNull(movie.get().getId());
    assertEquals("Movie Three", movie.get().getName());
  }

  @Test
  void testFindById_NotFound() {
    Optional<MovieRecord> movie = repository.findById(3);
    assertFalse(movie.isPresent());
  }

}

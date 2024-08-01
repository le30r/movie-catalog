package com.rntgroup.catalog.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rntgroup.catalog.repositories.entity.MovieRecord;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieRepositoryFacadeTest {

  @Mock
  private MovieExternalRepository externalRepository;

  @Mock
  private MovieInternalRepository internalRepository;

  @InjectMocks
  private MovieRepositoryFacade facade;

  private MovieRecord movie1;
  private MovieRecord movie2;

  @BeforeEach
  void setUp() {
    movie1 = new MovieRecord(1, "Movie One", "Director One", "Actor1;Actor2", "Genre1;Genre2");
    movie2 = new MovieRecord(2, "Movie Two", "Director Two", "Actor3;Actor4", "Genre3;Genre4");
  }

  @Test
  void testSave() {
    when(externalRepository.save(any(MovieRecord.class))).thenReturn(movie1);

    MovieRecord result = facade.save(movie1);

    assertNotNull(result);
    assertEquals(movie1.getId(), result.getId());
    verify(externalRepository, times(1)).save(movie1);
  }

  @Test
  void testDeleteById() {
    doNothing().when(externalRepository).deleteById(1);

    facade.deleteById(1);

    verify(externalRepository, times(1)).deleteById(1);
  }

  @Test
  void testFindAll() {
    when(internalRepository.findAll()).thenReturn(Arrays.asList(movie1));
    when(externalRepository.findAll()).thenReturn(Arrays.asList(movie2));

    List<MovieRecord> result = facade.findAll();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.contains(movie1));
    assertTrue(result.contains(movie2));
  }

  @Test
  void testFindById_InternalFirst() {
    when(internalRepository.findById(1)).thenReturn(Optional.of(movie1));

    Optional<MovieRecord> result = facade.findById(1);

    assertTrue(result.isPresent());
    assertEquals(movie1.getId(), result.get().getId());
  }

  @Test
  void testFindById_ExternalSecond() {
    when(internalRepository.findById(2)).thenReturn(Optional.empty());
    when(externalRepository.findById(2)).thenReturn(Optional.of(movie2));

    Optional<MovieRecord> result = facade.findById(2);

    assertTrue(result.isPresent());
    assertEquals(movie2.getId(), result.get().getId());
  }

  @Test
  void testFindById_NotFound() {
    when(internalRepository.findById(3)).thenReturn(Optional.empty());
    when(externalRepository.findById(3)).thenReturn(Optional.empty());

    Optional<MovieRecord> result = facade.findById(3);

    assertFalse(result.isPresent());
  }
}

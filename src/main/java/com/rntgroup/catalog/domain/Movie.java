package com.rntgroup.catalog.domain;

import java.util.List;

public record Movie(
  String name,
  Director director,
  List<Actor> actors,
  List<Genre> genres
) {

}

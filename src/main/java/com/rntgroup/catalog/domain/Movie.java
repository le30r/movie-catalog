package com.rntgroup.catalog.domain;

import java.util.List;

public record Movie(
    Integer id,
    String name,
    Director director,
    List<Actor> actors,
    List<Genre> genres
) {

}

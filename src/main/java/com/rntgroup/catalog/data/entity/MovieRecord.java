package com.rntgroup.catalog.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRecord {
  private Integer id;
  private String name;
  private String director;
  private String actors;
  private String genres;
}

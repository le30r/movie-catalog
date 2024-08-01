package com.rntgroup.catalog.repositories.exceptions;

public class CsvFileNotFoundException extends RuntimeException {
  private String path;

  @Override
  public String toString() {
    return "CsvFileNotFoundException{" +
           "path='" + path + '\'' +
           '}';
  }

  public CsvFileNotFoundException(String path) {
    this.path = path;
  }
}

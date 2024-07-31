package com.rntgroup.catalog.view;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CatalogView implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    System.out.println("hello, world!");
  }
}

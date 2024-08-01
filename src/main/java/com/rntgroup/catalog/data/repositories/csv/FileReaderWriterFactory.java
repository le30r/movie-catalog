package com.rntgroup.catalog.data.repositories.csv;

import com.rntgroup.catalog.data.exceptions.DataProcessingException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class FileReaderWriterFactory {

  public Reader getReader(String filePath) {
    try {
      Path path = Paths.get(filePath);
      if (path.isAbsolute() || Files.exists(path)) {
        return new FileReader(filePath);
      } else {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(filePath) != null) {
          return new InputStreamReader(classLoader.getResourceAsStream(filePath));
        } else {
          throw new IOException("File not found in resources or on disk: " + filePath);
        }
      }
    } catch (IOException e) {
      throw new DataProcessingException(e);
    }
  }

  public FileWriter getWriter(String path) {
    try {
      File file = new File(path);
      return new FileWriter(file);
    } catch (IOException e) {
      throw new DataProcessingException(e);
    }
  }
}

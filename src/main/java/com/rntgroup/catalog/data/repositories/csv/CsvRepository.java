package com.rntgroup.catalog.data.repositories.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class CsvRepository<T>  {

  private final FileReaderWriterFactory fileReaderWriterFactory;

  @Autowired
  protected CsvRepository(FileReaderWriterFactory fileReaderFactory) {
    this.fileReaderWriterFactory = fileReaderFactory;
  }

  protected List<T> readAll(String path, Class<T> clazz) throws IOException {
    try(Reader reader = fileReaderWriterFactory.getReader(path)) {
      return new CsvToBeanBuilder<T>(reader)
          .withType(clazz)
          .build()
          .parse();
    }
  }

  protected void writeAll(List<T> list, String path)
      throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
    try(Writer writer = fileReaderWriterFactory.getWriter(path)) {
      StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
      beanToCsv.write(list);
    }
  }
}

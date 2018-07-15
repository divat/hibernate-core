package de.laliluna.other.query;

import java.io.Serializable;
import java.text.MessageFormat;


public class AuthorReport {
  private Integer id;

  private String name;

  private Long totalBooks;

  private Long totalChapters;

  public AuthorReport() {
  }
  
  public AuthorReport(Integer id, String name, Long  totalBooks, Long totalChapters) {
    this.id = id;
    this.name = name;
    this.totalBooks = totalBooks;
    this.totalChapters = totalChapters;
  }

  public String toString() {
    return MessageFormat
            .format("{0}: id={1} name={2} totalBooks={3} totalChapters={4}",
                    new Object[]{getClass().getSimpleName(), id, name, totalBooks, totalChapters});

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getTotalBooks() {
    return totalBooks;
  }

  public void setTotalBooks(Long totalBooks) {
    this.totalBooks = totalBooks;
  }

  public Long getTotalChapters() {
    return totalChapters;
  }

  public void setTotalChapters(Long totalChapters) {
    this.totalChapters = totalChapters;
  }
}

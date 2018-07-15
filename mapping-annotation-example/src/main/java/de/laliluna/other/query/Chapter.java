package de.laliluna.other.query;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;


@Entity
public class Chapter {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
  @SequenceGenerator(name = "book_seq", sequenceName = "book_id_seq", allocationSize = 500)
  private Integer id;

  private String name;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private String content;

  public Chapter() {
  }

  public Chapter(String name) {
    this.name = name;
  }

  public Chapter(String name, String content) {
    this.name = name;
    this.content = content;
  }

  public String toString() {
    return MessageFormat.format("{0}: id={1} name={2}", new Object[]{getClass().getSimpleName(), id, name});
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}

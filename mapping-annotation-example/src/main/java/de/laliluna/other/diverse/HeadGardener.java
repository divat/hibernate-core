package de.laliluna.other.diverse;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

@Entity
public class HeadGardener {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  private String lastChangeUser;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastChangeDate;

  public HeadGardener() {
  }

  public HeadGardener(String name) {
    this.name = name;
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

  public String getLastChangeUser() {
    return lastChangeUser;
  }

  public Date getLastChangeDate() {
    return lastChangeDate;
  }
}

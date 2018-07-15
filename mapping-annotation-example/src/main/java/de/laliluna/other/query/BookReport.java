package de.laliluna.other.query;

import java.text.MessageFormat;


public class BookReport {
  private Integer idInReport;

  private String nameInReport;

  public BookReport() {
  }

  public BookReport(Integer idInReport, String nameInReport) {
    this.idInReport = idInReport;
    this.nameInReport = nameInReport;
  }

  public Integer getIdInReport() {
    return idInReport;
  }

  public void setIdInReport(Integer idInReport) {
    this.idInReport = idInReport;
  }

  public String getNameInReport() {
    return nameInReport;
  }

  public void setNameInReport(String nameInReport) {
    this.nameInReport = nameInReport;
  }

    public String toString() {
    return MessageFormat.format("{0}: id={1} name={2}", new Object[]{getClass().getSimpleName(), idInReport, nameInReport});
  }
}

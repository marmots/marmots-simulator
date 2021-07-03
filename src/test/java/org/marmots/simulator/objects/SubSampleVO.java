package org.marmots.simulator.objects;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class SubSampleVO {
  private Integer id;
  private Long number;
  private Date date;
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

}

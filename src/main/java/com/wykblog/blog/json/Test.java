package com.wykblog.blog.json;

import java.sql.Timestamp;
import java.util.Date;

public class Test {
  private Date date;
  
  private Date sqlDate;
  
  private Timestamp timestamp;
  
  public Test() {
    Date now = new Date();
    setDate(now);
    Date sqlDate = new Date(now.getTime());
    setSqlDate(sqlDate);
    Timestamp timestamp = new Timestamp(now.getTime());
    setTimestamp(timestamp);
  }
  
  public Date getDate() {
    return this.date;
  }
  
  public void setDate(Date date) {
    this.date = date;
  }
  
  public Date getSqlDate() {
    return this.sqlDate;
  }
  
  public void setSqlDate(Date sqlDate) {
    this.sqlDate = sqlDate;
  }
  
  public Timestamp getTimestamp() {
    return this.timestamp;
  }
  
  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
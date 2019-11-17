package com.sample;


public class TOperation {

  private long id;
  private String operationName;
  private java.sql.Date operationDate;
  private String orderType;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }


  public java.sql.Date getOperationDate() {
    return operationDate;
  }

  public void setOperationDate(java.sql.Date operationDate) {
    this.operationDate = operationDate;
  }


  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

}

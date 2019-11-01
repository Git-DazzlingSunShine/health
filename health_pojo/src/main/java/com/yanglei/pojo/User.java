package com.yanglei.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

  private long id;
  private String userName;
  private String password;
  private String name;
  private long age;
  private long sex;
  private java.sql.Date birthday;
  private java.sql.Timestamp created;
  private java.sql.Timestamp updated;

}

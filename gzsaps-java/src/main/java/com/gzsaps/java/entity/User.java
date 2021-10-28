package com.gzsaps.java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
  @Id
  private String username;
  private String password;
  private String realname;
  private String tel;
  private String address;
  private Integer role;
}

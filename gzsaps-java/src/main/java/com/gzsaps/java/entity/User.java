package com.gzsaps.java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
  @Id
  private StringBuilder username;
  private StringBuilder password;
  private StringBuilder realname;
  private StringBuilder tel;
  private StringBuilder address;
  private Integer role;
}

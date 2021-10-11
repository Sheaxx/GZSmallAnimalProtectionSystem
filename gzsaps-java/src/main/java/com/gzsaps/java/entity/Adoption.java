package com.gzsaps.java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Adoption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer adoptionid;
  private StringBuilder title;
  private StringBuilder content;
  private StringBuilder author;
  private Timestamp createtime;
  private Timestamp lastmodifiedtime;
  private Integer status;
  private StringBuilder adopter;
  private Timestamp adopttime;
}

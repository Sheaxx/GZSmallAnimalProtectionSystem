package com.gzsaps.java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Adoption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer adoptionid;
  private String title;
  private String content;
  private String author;
  private Date createtime;
  private Date lastmodifiedtime;
  private Integer status;
  private String adopter;
  private Date adopttime;
}

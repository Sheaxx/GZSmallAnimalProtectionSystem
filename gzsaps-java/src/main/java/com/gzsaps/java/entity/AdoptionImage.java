package com.gzsaps.java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class AdoptionImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer imageid;
  private String data;
  private Integer adoptionid;
}

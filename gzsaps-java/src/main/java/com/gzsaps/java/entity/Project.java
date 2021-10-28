package com.gzsaps.java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer projectid;
  private String title;
  private String content;
  private String address;
  private Date begintime;
  private Date endtime;
  private String principal;
  private String principaltel;
  private String author;
  private Date createtime;
  private Date lastmodifiedtime;
  private Integer status;
  private String applicantlist;
}

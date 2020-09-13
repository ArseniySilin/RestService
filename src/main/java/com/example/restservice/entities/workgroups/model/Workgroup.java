package com.example.restservice.entities.workgroups.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "workgroups")
public class Workgroup {
  @Id
  @Column(name = "key")
  private String key;

  @Column(name = "name")
  private String name;

  @Column(name = "publicid")
  private String publicId;

  @Column(name = "createddatetimeutc")
  private LocalDateTime createdDateTimeUtc;

  @Column(name = "createduserkey")
  private String createdUserKey;

  public Workgroup() {}

  public Workgroup(String key, String name, String publicId, String createdUserKey) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    this.createdDateTimeUtc = currentDateTime;

    this.key = key;
    this.name = name;
    this.publicId = publicId;
    this.createdUserKey = createdUserKey;
  }

  public Workgroup(String key, String name, String publicId, LocalDateTime createdDateTimeUtc, String createdUserKey) {
    this.key = key;
    this.name = name;
    this.publicId = publicId;
    this.createdDateTimeUtc = createdDateTimeUtc;
    this.createdUserKey = createdUserKey;
  }
}

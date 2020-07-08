package com.example.restservice.workgroups.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Workgroup {
  private String key;
  private String name;
  private String publicId;
  private LocalDateTime createdDateTimeUtc;
  private String createdUserKey;

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

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public String getPublicId() {
    return publicId;
  }

  public LocalDateTime getCreatedDateTimeUtc() {
    return createdDateTimeUtc;
  }

  public String getCreatedUserKey() {
    return createdUserKey;
  }
}

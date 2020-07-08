package com.example.restservice.templates.model;

import java.time.LocalDateTime;

public class Template {
  private String key;
  private String name;
  private LocalDateTime createdDateTimeUtc;
  private LocalDateTime updatedDateTimeUtc;
  private String createdUserFirstName;
  private String createdUserLastName;
  private String createdUserKey;
  private String createdUserName;
  private String folderKey;
  private boolean isPart;
  private String workGroupKey;

  public Template(
    String key,
    String name,
    LocalDateTime createdDateTimeUtc,
    LocalDateTime updatedDateTimeUtc,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserKey,
    String createdUserName,
    String folderKey,
    boolean isPart,
    String workGroupKey
  ) {
    this.key = key;
    this.name = name;
    this.createdDateTimeUtc = createdDateTimeUtc;
    this.updatedDateTimeUtc = updatedDateTimeUtc;
    this.createdUserFirstName = createdUserFirstName;
    this.createdUserLastName = createdUserLastName;
    this.createdUserKey = createdUserKey;
    this.createdUserName = createdUserName;
    this.folderKey = folderKey;
    this.isPart = isPart;
    this.workGroupKey = workGroupKey;
  }

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getCreatedDateTimeUtc() {
    return createdDateTimeUtc;
  }

  public LocalDateTime getUpdatedDateTimeUtc() {
    return updatedDateTimeUtc;
  }

  public String getCreatedUserFirstName() {
    return createdUserFirstName;
  }

  public String getCreatedUserLastName() {
    return createdUserLastName;
  }

  public String getCreatedUserKey() {
    return createdUserKey;
  }

  public String getCreatedUserName() {
    return createdUserName;
  }

  public String getFolderKey() {
    return folderKey;
  }

  public boolean isPart() {
    return isPart;
  }

  public String getWorkGroupKey() {
    return workGroupKey;
  }
}

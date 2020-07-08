package com.example.restservice.templates.model;

import java.time.LocalDateTime;

public class Template {
  String key;
  String name;
  LocalDateTime createdDateTimeUtc;
  LocalDateTime updatedDateTimeUtc;
  String createdUserFirstName;
  String createdUserLastName;
  String createdUserKey;
  String createdUserName;
  String folderKey;
  boolean isPart;
  String workGroupKey;


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
}

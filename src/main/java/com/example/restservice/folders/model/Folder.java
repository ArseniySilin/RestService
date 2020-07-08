package com.example.restservice.folders.model;

import java.time.LocalDateTime;

public class Folder {
  String key;
  String parentFolderKey;
  String parentFolderName;
  int folderType;
  String name;
  LocalDateTime createdDateTimeUtc;
  LocalDateTime updatedDateTimeUtc;
  String createdUserFirstName;
  String createdUserLastName;
  String createdUserKey;
  String createdUserName;
  String workGroupKey;

  public Folder(
    String key,
    String parentFolderKey,
    String parentFolderName,
    int folderType,
    String name,
    LocalDateTime createdDateTimeUtc,
    LocalDateTime updatedDateTimeUtc,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserKey,
    String createdUserName,
    String workGroupKey
  ) {
    this.key = key;
    this.parentFolderKey = parentFolderKey;
    this.parentFolderName = parentFolderName;
    this.folderType = folderType;
    this.name = name;
    this.createdDateTimeUtc = createdDateTimeUtc;
    this.updatedDateTimeUtc = updatedDateTimeUtc;
    this.createdUserFirstName = createdUserFirstName;
    this.createdUserLastName = createdUserLastName;
    this.createdUserKey = createdUserKey;
    this.createdUserName = createdUserName;
    this.workGroupKey = workGroupKey;
  }
}

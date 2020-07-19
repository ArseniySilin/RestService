package com.example.restservice.common;

import java.time.LocalDateTime;

public abstract class AllWithFoldersEntity {
  String name;
  String createdUserName;
  LocalDateTime createdDateTimeUtc;
  LocalDateTime updatedDateTimeUtc;

  public String getName() {
    return name;
  }

  public String getCreatedUserName() {
    return createdUserName;
  }

  public LocalDateTime getCreatedDateTimeUtc() {
    return createdDateTimeUtc;
  }

  public LocalDateTime getUpdatedDateTimeUtc() {
    return updatedDateTimeUtc;
  }
}

package com.example.restservice.entities.common;

import lombok.Getter;

import java.time.LocalDateTime;

public abstract class AllWithFoldersEntity {
  @Getter
  String name;
  @Getter
  String createdUserName;
  @Getter
  LocalDateTime createdDateTimeUtc;
  @Getter
  LocalDateTime updatedDateTimeUtc;
}

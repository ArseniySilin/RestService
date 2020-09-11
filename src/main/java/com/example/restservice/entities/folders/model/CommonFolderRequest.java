package com.example.restservice.entities.folders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CommonFolderRequest {
  @Getter
  private String name;
  @Getter
  private int folderType;
  @Getter
  private String parentFolderKey;
  @Getter
  private String workgroupKey;
}

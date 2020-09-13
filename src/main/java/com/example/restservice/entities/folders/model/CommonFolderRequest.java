package com.example.restservice.entities.folders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonFolderRequest {
  private String name;
  private int folderType;
  private String parentFolderKey;
  private String workgroupKey;
}

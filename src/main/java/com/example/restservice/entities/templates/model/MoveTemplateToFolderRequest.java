package com.example.restservice.entities.templates.model;

public class MoveTemplateToFolderRequest {
  private String folderKey;

  public MoveTemplateToFolderRequest() {}

  public MoveTemplateToFolderRequest(String folderKey) {
    this.folderKey = folderKey;
  }

  public String getFolderKey() {
    return folderKey;
  }
}

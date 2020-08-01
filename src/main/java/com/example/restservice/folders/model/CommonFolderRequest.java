package com.example.restservice.folders.model;

public class CommonFolderRequest {
  private String name;
  private int folderType;
  private String parentFolderKey;
  private String workgroupKey;


  public CommonFolderRequest(String name, int folderType, String parentFolderKey, String workgroupKey) {
    this.name = name;
    this.folderType = folderType;
    this.workgroupKey = workgroupKey;
    this.parentFolderKey = parentFolderKey;
  }

  public String getName() {
    return name;
  }

  public int getFolderType() {
    return folderType;
  }

  public String getWorkgroupKey() {
    return workgroupKey;
  }

  public String getParentFolderKey() {
    return parentFolderKey;
  }
}

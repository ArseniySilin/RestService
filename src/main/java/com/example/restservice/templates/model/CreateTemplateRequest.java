package com.example.restservice.templates.model;

public class CreateTemplateRequest {
  private String content;
  private String name;
  private String folderKey;
  private String workgroupKey;

  public CreateTemplateRequest(String content, String name, String folderKey, String workgroupKey) {
    this.content = content;
    this.name = name;
    this.folderKey = folderKey;
    this.workgroupKey = workgroupKey;
  }

  public String getContent() {
    return content;
  }

  public String getName() {
    return name;
  }

  public String getFolderKey() {
    return folderKey;
  }

  public String getWorkgroupKey() {
    return workgroupKey;
  }
}

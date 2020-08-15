package com.example.restservice.entities.templates.model;

public class CreateTemplateRequest {
  private String content;
  private String name;
  private String folderKey;
  private String workgroupKey;
  private boolean isPart;

  public CreateTemplateRequest(String content, String name, String folderKey, String workgroupKey, boolean isPart) {
    this.content = content;
    this.name = name;
    this.folderKey = folderKey;
    this.workgroupKey = workgroupKey;
    this.isPart = isPart;
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

  public boolean isPart() {
    return isPart;
  }
}

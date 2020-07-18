package com.example.restservice.templates.model;

import java.time.LocalDateTime;
import java.util.List;

public class TemplateExtended extends Template {
  private String content;
  private String schema;
  private Object files;
  private List<Object> includedTemplateParts;
  private List<Object> slotDependencies;

  public TemplateExtended(
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
    String workGroupKey,
    String content,
    String schema
  ) {
    super(
      key,
      name,
      createdUserFirstName,
      createdUserLastName,
      createdUserName,
      createdUserKey,
      createdDateTimeUtc,
      updatedDateTimeUtc,
      folderKey,
      isPart,
      workGroupKey
    );

    this.content = content;
    this.schema = schema;
  }

  public String getContent() {
    return content;
  }

  public String getSchema() {
    return schema;
  }

  public Object getFiles() {
    return files;
  }

  public List<Object> getIncludedTemplateParts() {
    return includedTemplateParts;
  }

  public List<Object> getSlotDependencies() {
    return slotDependencies;
  }
}

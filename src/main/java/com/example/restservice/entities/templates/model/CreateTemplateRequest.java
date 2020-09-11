package com.example.restservice.entities.templates.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateTemplateRequest {
  private String content;
  private String name;
  private String folderKey;
  private String workgroupKey;
  private boolean isPart;
}

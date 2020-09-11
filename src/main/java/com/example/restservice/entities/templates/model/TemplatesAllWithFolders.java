package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.folders.model.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class TemplatesAllWithFolders {
  @Getter
  List<Template> templates;
  @Getter
  List<Folder> folders;
  @Getter
  PageInfo pageInfo;
}

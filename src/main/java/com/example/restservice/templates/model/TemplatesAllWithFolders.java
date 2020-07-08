package com.example.restservice.templates.model;

import com.example.restservice.folders.model.Folder;

import java.util.List;

public class TemplatesAllWithFolders {
  List<Template> templates;
  List<Folder> folders;
  PageInfo pageInfo;

  public TemplatesAllWithFolders(List<Template> templates, List<Folder> folders, PageInfo pageInfo) {
    this.templates = templates;
    this.folders = folders;
    this.pageInfo = pageInfo;
  }
}

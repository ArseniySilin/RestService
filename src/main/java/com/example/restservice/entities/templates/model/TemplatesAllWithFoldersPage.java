package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.folders.model.Folder;

import java.util.List;

public class TemplatesAllWithFoldersPage {
  List<Template> templates;
  List<Folder> folders;
  PageInfo pageInfo;

  public TemplatesAllWithFoldersPage(List<Template> templates, List<Folder> folders, PageInfo pageInfo) {
    this.templates = templates;
    this.folders = folders;
    this.pageInfo = pageInfo;
  }

  public List<Template> getTemplates() {
    return templates;
  }

  public List<Folder> getFolders() {
    return folders;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }
}

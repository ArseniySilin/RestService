package com.example.restservice.workgroups.model;


import com.example.restservice.folders.Folder;
import com.example.restservice.templates.Template;
import com.example.restservice.workgroups.model.Workgroup;

import java.util.List;

public class WorkgroupsAllWithFolders extends Workgroup {
  List<Template> templates;
  List<Folder> folders;

  public WorkgroupsAllWithFolders(
    String key,
    String name,
    String publicId,
    String createdUserKey,
    List<Template> templates,
    List<Folder> folders) {

    super(key, name, publicId, createdUserKey);

    this.templates = templates;
    this.folders = folders;
  }
}

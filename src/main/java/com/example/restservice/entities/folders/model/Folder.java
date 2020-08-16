package com.example.restservice.entities.folders.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import com.example.restservice.entities.common.AllWithFoldersEntity;

@Entity
@Table(name = "folders")
public class Folder extends AllWithFoldersEntity implements Serializable {
  @Id
  @Column(name = "key")
  private String key;

  @Column(name = "parentfolderkey")
  private String parentFolderKey;

  @Column(name = "parentfoldername")
  private String parentFolderName;

  @Column(name = "foldertype")
  private int folderType;

  @Column(name = "name")
  private String name;

  @Column(name = "createddatetimeutc")
  private LocalDateTime createdDateTimeUtc;

  @Column(name = "updateddatetimeutc")
  private LocalDateTime updatedDateTimeUtc;

  @Column(name = "createduserfirstname")
  private String createdUserFirstName;

  @Column(name = "createduserlastname")
  private String createdUserLastName;

  @Column(name = "createduserkey")
  private String createdUserKey;

  @Column(name = "createdusername")
  private String createdUserName;

  @Column(name = "workgroupkey")
  private String workGroupKey;

  public Folder() {}

  public Folder(
    String key,
    String name,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserName,
    String createdUserKey,
    LocalDateTime createdDateTimeUtc,
    LocalDateTime updatedDateTimeUtc,
    int folderType,
    String parentFolderKey,
    String parentFolderName,
    String workGroupKey
  ) {
    this.key = key;
    this.parentFolderKey = parentFolderKey;
    this.parentFolderName = parentFolderName;
    this.folderType = folderType;
    this.name = name;
    this.createdDateTimeUtc = createdDateTimeUtc;
    this.updatedDateTimeUtc = updatedDateTimeUtc;
    this.createdUserFirstName = createdUserFirstName;
    this.createdUserLastName = createdUserLastName;
    this.createdUserKey = createdUserKey;
    this.createdUserName = createdUserName;
    this.workGroupKey = workGroupKey;
  }

  public String getKey() {
    return key;
  }

  public String getParentFolderKey() {
    return parentFolderKey;
  }

  public String getParentFolderName() {
    return parentFolderName;
  }

  public int getFolderType() {
    return folderType;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getCreatedDateTimeUtc() {
    return createdDateTimeUtc;
  }

  public LocalDateTime getUpdatedDateTimeUtc() {
    return updatedDateTimeUtc;
  }

  public String getCreatedUserFirstName() {
    return createdUserFirstName;
  }

  public String getCreatedUserLastName() {
    return createdUserLastName;
  }

  public String getCreatedUserKey() {
    return createdUserKey;
  }

  public String getCreatedUserName() {
    return createdUserName;
  }

  public String getWorkGroupKey() {
    return workGroupKey;
  }
}

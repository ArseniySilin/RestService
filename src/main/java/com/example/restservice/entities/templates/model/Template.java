package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.common.AllWithFoldersEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
public class Template extends AllWithFoldersEntity implements Serializable {
  @Id
  @Column(name = "key")
  private String key;

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

  @Column(name = "folderkey")
  private String folderKey;

  @Column(name = "ispart")
  private boolean isPart;

  public Template() {}

  public Template(
    String key,
    String name,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserName,
    String createdUserKey,
    LocalDateTime createdDateTimeUtc,
    LocalDateTime updatedDateTimeUtc,
    String folderKey,
    boolean isPart,
    String workGroupKey
  ) {
    this.key = key;
    this.name = name;
    this.createdDateTimeUtc = createdDateTimeUtc;
    this.updatedDateTimeUtc = updatedDateTimeUtc;
    this.createdUserFirstName = createdUserFirstName;
    this.createdUserLastName = createdUserLastName;
    this.createdUserKey = createdUserKey;
    this.createdUserName = createdUserName;
    this.folderKey = folderKey;
    this.isPart = isPart;
    this.workGroupKey = workGroupKey;
  }

  public String getKey() {
    return key;
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

  public String getFolderKey() {
    return folderKey;
  }

  public boolean isPart() {
    return isPart;
  }

  public String getWorkGroupKey() {
    return workGroupKey;
  }
}

package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.common.AllWithFoldersEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class TemplatesAllWithFoldersPage2 extends AllWithFoldersEntity implements Serializable {
  @Column(name = "foldertype")
  private int folderType;

  @Column(name = "parentfolderkey")
  private String parentFolderKey;

  @Column(name = "parentfoldername")
  private String parentFolderName;

  @Column(name = "folderkey")
  private String folderKey;

  @Column(name = "ispart")
  private boolean isPart;

  @Column(name = "entitytype")
  private String entityType;

  @Builder
  public TemplatesAllWithFoldersPage2(
    String key,
    String workGroupKey,
    String name,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserName,
    String createdUserKey,
    LocalDateTime createdDateTimeUtc,
    LocalDateTime updatedDateTimeUtc,
    String folderKey,
    boolean isPart,
    Integer folderType,
    String parentFolderKey,
    String parentFolderName,
    String entityType
  ) {
    super(
      key,
      workGroupKey,
      name,
      createdUserFirstName,
      createdUserLastName,
      createdUserName,
      createdUserKey,
      createdDateTimeUtc,
      updatedDateTimeUtc
    );
    this.folderKey = folderKey;
    this.isPart = isPart;
    this.folderType = folderType;
    this.parentFolderKey = parentFolderKey;
    this.parentFolderName = parentFolderName;
    this.entityType = entityType;
  }
}

package com.example.restservice.entities.folders.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import com.example.restservice.entities.common.AllWithFoldersEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "folders")
public class Folder extends AllWithFoldersEntity implements Serializable {
  @Column(name = "foldertype")
  private int folderType;

  @Column(name = "parentfolderkey")
  private String parentFolderKey;

  @Column(name = "parentfoldername")
  private String parentFolderName;

  @Builder
  public Folder(
    String key,
    String workGroupKey,
    String name,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserName,
    String createdUserKey,
    LocalDateTime createdDateTimeUtc,
    LocalDateTime updatedDateTimeUtc,
    int folderType,
    String parentFolderKey,
    String parentFolderName
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

    this.folderType = folderType;
    this.parentFolderKey = parentFolderKey;
    this.parentFolderName = parentFolderName;
  }
}


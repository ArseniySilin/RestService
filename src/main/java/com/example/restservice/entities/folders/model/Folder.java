package com.example.restservice.entities.folders.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import com.example.restservice.entities.common.AllWithFoldersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "folders")
public class Folder extends AllWithFoldersEntity implements Serializable {
  @Id
  @Column(name = "key")
  @Getter
  private String key;

  @Column(name = "name")
  @Getter
  private String name;

  @Column(name = "createduserfirstname")
  @Getter
  private String createdUserFirstName;

  @Column(name = "createduserlastname")
  @Getter
  private String createdUserLastName;

  @Column(name = "createdusername")
  @Getter
  private String createdUserName;

  @Column(name = "createduserkey")
  @Getter
  private String createdUserKey;

  @Column(name = "createddatetimeutc")
  @Getter
  private LocalDateTime createdDateTimeUtc;

  @Column(name = "updateddatetimeutc")
  @Getter
  private LocalDateTime updatedDateTimeUtc;

  @Column(name = "foldertype")
  @Getter
  private int folderType;

  @Column(name = "parentfolderkey")
  @Getter
  private String parentFolderKey;

  @Column(name = "parentfoldername")
  @Getter
  private String parentFolderName;

  @Column(name = "workgroupkey")
  @Getter
  private String workGroupKey;
}

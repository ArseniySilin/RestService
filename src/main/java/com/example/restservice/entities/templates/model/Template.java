package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.common.AllWithFoldersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "templates")
public class Template extends AllWithFoldersEntity implements Serializable {
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

  @Column(name = "folderkey")
  @Getter
  private String folderKey;

  @Column(name = "ispart")
  @Getter
  private boolean isPart;

  @Column(name = "workgroupkey")
  @Getter
  private String workGroupKey;
}

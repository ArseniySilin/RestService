package com.example.restservice.entities.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AllWithFoldersEntity {
  @Id
  @Column(name = "key")
  String key;

  @Column(name = "workgroupkey")
  private String workGroupKey;

  @Column(name = "name")
  String name;

  @Column(name = "createduserfirstname")
  String createdUserFirstName;

  @Column(name = "createduserlastname")
  String createdUserLastName;

  @Column(name = "createdusername")
  String createdUserName;

  @Column(name = "createduserkey")
  String createdUserKey;

  @Column(name = "createddatetimeutc")
  LocalDateTime createdDateTimeUtc;

  @Column(name = "updateddatetimeutc")
  LocalDateTime updatedDateTimeUtc;
}

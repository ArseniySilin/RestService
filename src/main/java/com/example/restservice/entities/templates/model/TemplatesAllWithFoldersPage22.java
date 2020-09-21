package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.common.AllWithFoldersEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity(name = "TemplatesAllWithFoldersPage22")
@NamedNativeQuery(
  name="TemplatesAllWithFoldersPage22.findAllWithFolders",
  query = "" +
    "SELECT " +
    "key, " +
    "workgroupkey, " +
    "name, " +
    "createduserfirstname, " +
    "createduserlastname, " +
    "createdusername, " +
    "createduserkey, " +
    "createddatetimeutc, " +
    "updateddatetimeutc, " +
    // templates fields
    "folderkey, " +
    "ispart, " +
    // default fields from folders
    "0 as foldertype, " +
    "NULL as parentfolderkey, " +
    "NULL as parentfoldername, " +
    // entity type
    "'template' as entitytype " +
    "FROM templates WHERE " +
      "workgroupkey = ?1 AND " +
      "createduserkey = ?2 AND " +
      "(folderkey IS NULL OR folderkey = cast(?3 AS VARCHAR)) " +
    "UNION " +
    "SELECT " +
    "key, " +
    "workgroupkey, " +
    "name, " +
    "createduserfirstname, " +
    "createduserlastname, " +
    "createdusername, " +
    "createduserkey, " +
    "createddatetimeutc, " +
    "updateddatetimeutc, " +
    // default fields from templates
    "NULL as folderkey, " +
    "FALSE as ispart, " +
    // folder fields
    "foldertype, " +
    "parentfolderkey, " +
    "parentfoldername, " +
    // entity type
    "'folder' as entitytype " +
    "FROM folders WHERE " +
      "workgroupkey = ?1 AND " +
      "createduserkey = ?2 AND " +
      "(parentfolderkey IS NULL OR parentfolderkey = cast(?3 AS VARCHAR))",
  resultSetMapping="AllWithFoldersMapping"
)
@SqlResultSetMapping(
  name="AllWithFoldersMapping",
  classes=@ConstructorResult(
    targetClass=TemplatesAllWithFoldersPage22.class,
    columns={
      @ColumnResult(name="key"),
      @ColumnResult(name="workgroupkey"),
      @ColumnResult(name="name"),
      @ColumnResult(name="createduserfirstname"),
      @ColumnResult(name="createduserlastname"),
      @ColumnResult(name="createdusername"),
      @ColumnResult(name="createduserkey"),
      @ColumnResult(name="createddatetimeutc", type=Date.class),
      @ColumnResult(name="updateddatetimeutc", type=Date.class),
      @ColumnResult(name="folderkey"),
      @ColumnResult(name="ispart", type=Boolean.class),
      @ColumnResult(name="foldertype", type=Integer.class),
      @ColumnResult(name="parentfolderkey"),
      @ColumnResult(name="parentfoldername"),
      @ColumnResult(name="entitytype")
    }))
@IdClass(TemplatesAllWithFoldersPage22.class)
public class TemplatesAllWithFoldersPage22 extends AllWithFoldersEntity implements Serializable {
  @Id
  @Column(name = "parentfolderkey")
  private String parentFolderKey;

  @Column(name = "parentfoldername")
  private String parentFolderName;

  @Column(name = "entitytype")
  private String entityType;

  @Column(name = "folderkey")
  private String folderKey;

  @Column(name = "foldertype")
  private int folderType;

  @Column(name = "ispart")
  private boolean isPart;

  @Builder
  public TemplatesAllWithFoldersPage22(
    String key,
    String workGroupKey,
    String name,
    String createdUserFirstName,
    String createdUserLastName,
    String createdUserName,
    String createdUserKey,
    Date createdDateTimeUtc,
    Date updatedDateTimeUtc,
    String folderKey,
    Boolean isPart,
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
      createdDateTimeUtc.toInstant().atZone(ZoneId.systemDefault())
        .toLocalDateTime(),
      updatedDateTimeUtc.toInstant().atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    );
    this.folderKey = folderKey;
    this.isPart = isPart;
    this.folderType = folderType;
    this.parentFolderKey = parentFolderKey;
    this.parentFolderName = parentFolderName;
    this.entityType = entityType;
  }
}

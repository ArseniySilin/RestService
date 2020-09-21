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
@Entity(name = "TemplatesAllWithFoldersEntity")
@NamedNativeQuery(
  name="TemplatesAllWithFoldersEntity.findAllWithFolders",
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
      "(CAST(?3 AS VARCHAR) IS NOT DISTINCT FROM folderkey) " +
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
      "(CAST(?3 AS VARCHAR) IS NOT DISTINCT FROM parentfolderkey)",
  resultSetMapping="AllWithFoldersMapping"
)
@SqlResultSetMapping(
  name="AllWithFoldersMapping",
  classes=@ConstructorResult(
    targetClass= TemplatesAllWithFoldersEntity.class,
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
@IdClass(TemplatesAllWithFoldersEntity.class)
public class TemplatesAllWithFoldersEntity extends AllWithFoldersEntity implements Serializable {
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
  public TemplatesAllWithFoldersEntity(
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

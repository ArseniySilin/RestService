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

    "folderkey, " +
    "ispart, " +

    "0 as foldertype, " +
    "NULL as parentfolderkey, " +
    "NULL as parentfoldername, " +

    "'template' as entitytype " +
    "FROM templates WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru' " +
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

    "NULL as folderkey, " +
    "FALSE as ispart, " +

    "foldertype, " +
    "parentfolderkey, " +
    "parentfoldername, " +

    "'folder' as entitytype " +
    "FROM folders WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru'",
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
      @ColumnResult(name="createddatetimeutc"),
      @ColumnResult(name="updateddatetimeutc"),
      @ColumnResult(name="folderkey"),
      @ColumnResult(name="ispart", type=Boolean.class),
      @ColumnResult(name="foldertype"),
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

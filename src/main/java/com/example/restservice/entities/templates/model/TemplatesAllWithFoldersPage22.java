package com.example.restservice.entities.templates.model;

import com.example.restservice.entities.common.AllWithFoldersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Entity(name = "TemplatesAllWithFoldersPage22")
@SqlResultSetMapping(
  name="AllWithFoldersMapping",
  classes=@ConstructorResult(
    targetClass=TemplatesAllWithFoldersPage22.class,
    columns={
      @ColumnResult(name="key"),
      @ColumnResult(name="name"),
      @ColumnResult(name="parentfolderkey"),
      @ColumnResult(name="parentfoldername"),
      @ColumnResult(name="entitytype")
    }))
@NamedNativeQuery(
  name="TemplatesAllWithFoldersPage22.findAllWithFolders",
  query = "" +
    "SELECT " +
    "key, " +
    "name, " +
    "NULL as parentfolderkey, " +
    "NULL as parentfoldername, " +
    "'template' as entitytype " +
    "FROM templates WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru' " +
    "UNION " +
    "SELECT " +
    "key, " +
    "name, " +
    "parentfolderkey, " +
    "parentfoldername, " +
    "'folder' as entitytype " +
    "FROM folders WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru'",
  resultSetMapping="AllWithFoldersMapping"
)
public class TemplatesAllWithFoldersPage22  implements Serializable {//extends AllWithFoldersEntity
//  @Column(name = "foldertype")
//  private int folderType;

  @Column(name = "key")
  private String key;

  @Column(name = "name")
  private String name;

  @Id
  @Column(name = "parentfolderkey")
  private String parentFolderKey;

  @Column(name = "parentfoldername")
  private String parentFolderName;

  @Column(name = "entitytype")
  private String entityType;

//  @Column(name = "folderkey")
//  private String folderKey;
//
//  @Column(name = "ispart")
//  private boolean isPart;
//
//  @Column(name = "entitytype")
//  private String entityType;
//
//  @Builder
//  public TemplatesAllWithFoldersPage22(
//    String key,
//    String workGroupKey,
//    String name,
//    String createdUserFirstName,
//    String createdUserLastName,
//    String createdUserName,
//    String createdUserKey,
//    LocalDateTime createdDateTimeUtc,
//    LocalDateTime updatedDateTimeUtc,
////    String folderKey,
////    boolean isPart,
////    Integer folderType,
//    String parentFolderKey,
//    String parentFolderName,
//    String entityType
//  ) {
//    super(
//      key,
//      workGroupKey,
//      name,
//      createdUserFirstName,
//      createdUserLastName,
//      createdUserName,
//      createdUserKey,
//      createdDateTimeUtc,
//      updatedDateTimeUtc
//    );
////    this.folderKey = folderKey;
////    this.isPart = isPart;
////    this.folderType = folderType;
//    this.parentFolderKey = parentFolderKey;
//    this.parentFolderName = parentFolderName;
//    this.entityType = entityType;
//  }
}

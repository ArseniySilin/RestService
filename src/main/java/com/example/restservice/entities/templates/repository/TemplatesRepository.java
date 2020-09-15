package com.example.restservice.entities.templates.repository;

import com.example.restservice.entities.templates.model.Template;
import com.example.restservice.entities.templates.model.TemplatesAllWithFoldersPage2;
import com.example.restservice.entities.templates.model.TemplatesAllWithFoldersPage22;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TemplatesRepository extends JpaRepository<Template, String> {
  @Query("SELECT t FROM Template t WHERE t.workGroupKey = :workGroupKey AND (t.folderKey is NULL OR t.folderKey = :folderKey)")
  List<Template> findByWorkGroupKeyAndFolderKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("folderKey") String folderKey
  );

  @Modifying
  @Query("DELETE FROM Template t WHERE t.workGroupKey = :workGroupKey AND t.key = :key")
  void deleteByWorkGroupKeyAndKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("key") String key
  );

  @Query("SELECT t FROM Template t WHERE t.workGroupKey = :workGroupKey AND t.key = :key")
  Template findByWorkGroupKeyAndKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("key") String key
  );

  @Transactional
  @Modifying
  @Query("UPDATE Template t SET t.folderKey = :nextFolderKey WHERE t.workGroupKey = :workGroupKey AND t.key = :key")
  void moveToFolder(
    @Param("workGroupKey") String workGroupKey,
    @Param("key") String key,
    @Param("nextFolderKey") String nextFolderKey
  );

  String UNION_TEMPLATES_AND_FOLDERS_SQL_QUERY = "" +
    "SELECT \n" +
    "key,\n" +
    "workgroupkey,\n" +
    "name,\n" +
    "createduserfirstname,\n" +
    "createduserlastname,\n" +
    "createdusername,\n" +
    "createduserkey,\n" +
    "createddatetimeutc,\n" +
    "updateddatetimeutc,\n" +
    "\n" +
    "folderKey,\n" +
    "isPart,\n" +
    "\n" +
    "NULL as folderType,\n" +
    "NULL as parentfolderkey,\n" +
    "NULL as parentfoldername,\n" +
    "\n" +
    "'template' as entitytype\n" +
    "\n" +
    "FROM templates WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru'\n" +
    "UNION\n" +
    "SELECT \n" +
    "\n" +
    "key,\n" +
    "workgroupkey,\n" +
    "name,\n" +
    "createduserfirstname,\n" +
    "createduserlastname,\n" +
    "createdusername,\n" +
    "createduserkey,\n" +
    "createddatetimeutc,\n" +
    "updateddatetimeutc,\n" +
    "\n" +
    "NULL as folderkey,\n" +
    "NULL as ispart,\n" +
    "\n" +
    "foldertype,\n" +
    "parentfolderkey,\n" +
    "parentfoldername,\n" +
    "\n" +
    "'folder' as entitytype\n" +
    "\n" +
    "FROM folders WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru'";

//  @Query(value = UNION_TEMPLATES_AND_FOLDERS_SQL_QUERY, nativeQuery = true)
//  List<TemplatesAllWithFoldersPage2> findAllWithFolders();

  String UNION_TEMPLATES_AND_FOLDERS_SQL_QUERY_22 = "" +
    "SELECT " +
    "name, " +
    "NULL as parentfolderkey," +
    "NULL as parentfoldername " +
    "FROM templates WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru'" +
    " UNION " +
    "SELECT " +
    "name, " +
    "parentfolderkey, " +
    "parentfoldername " +
    "FROM folders WHERE workgroupkey = 'aaaaa111-1a1a-aa11-11a1-11111a111111' AND createdusername = 'test@docspro.ru'";

  @Query(value = UNION_TEMPLATES_AND_FOLDERS_SQL_QUERY_22, nativeQuery = true)
  List<Object> findAllWithFolders();
}

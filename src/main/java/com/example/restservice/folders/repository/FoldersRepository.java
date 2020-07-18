package com.example.restservice.folders.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.folders.model.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class FoldersRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private List<String> getFoldersKeysInWorkGroup(Connection con, String workGroupKey) {
    List<String> folderKeys = new ArrayList<>();

    try {
      PreparedStatement pstmt =
        con.prepareStatement("SELECT * FROM workgroups_folders WHERE workgroupkey = ?");
      pstmt.setString(1, workGroupKey);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        String folderKey = rs.getString("folderkey");
        folderKeys.add(folderKey);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return folderKeys;
  }

  public Map<String, Folder> getFolders(String workGroupKey, String parentFolderKey) {
    Map<String, Folder> folders = new HashMap<>();

    try {
      Connection con = DBCPDataSource.getConnection();

      List<String> folderKeys = getFoldersKeysInWorkGroup(con, workGroupKey);

      if (folderKeys.isEmpty()) return folders;

      String folderKeysPlaceholders = String.join(",", Collections.nCopies(folderKeys.size(), "?"));
      List<String> sqlParamsValues = new ArrayList<>(folderKeys);
      String sqlQuery;

      if (parentFolderKey == null) {
        sqlQuery = String.format(
          "SELECT * FROM folders WHERE key IN (%s) AND parentfolderkey IS NULL",
          folderKeysPlaceholders
        );
      } else {
        sqlQuery = String.format(
          "SELECT * FROM folders WHERE key IN (%s) AND parentfolderkey = ?",
          folderKeysPlaceholders
        );
        sqlParamsValues.add(parentFolderKey);
      }

      folders = jdbcTemplate.query(
        sqlQuery,
        sqlParamsValues.toArray(),
        (ResultSet rs) -> {
          HashMap<String, Folder> results = new HashMap<>();

          while (rs.next()) {
            String key = rs.getString("key");
            String name = rs.getString("name");
            String createdUserFirstName = rs.getString("createduserfirstname");
            String createdUserLastName = rs.getString("createduserlastname");
            String createdUserName = rs.getString("createdusername");
            String createdUserKey = rs.getString("createduserkey");
            LocalDateTime createdDateTimeUtc = rs.getTimestamp("createddatetimeutc").toLocalDateTime();
            LocalDateTime updatedDateTimeUtc = rs.getTimestamp("updateddatetimeutc").toLocalDateTime();
            int folderType = rs.getInt("folderType");
            String parentFolderName = rs.getString("parentfoldername");

            Folder folder = new Folder(
              key,
              name,
              createdUserFirstName,
              createdUserLastName,
              createdUserName,
              createdUserKey,
              createdDateTimeUtc,
              updatedDateTimeUtc,
              folderType,
              parentFolderKey,
              parentFolderName,
              workGroupKey
            );

            results.put(key, folder);
          }
          return results;
        });
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return folders;
  }
}

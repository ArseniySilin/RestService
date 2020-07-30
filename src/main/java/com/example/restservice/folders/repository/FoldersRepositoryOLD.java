package com.example.restservice.folders.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.folders.model.Folder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FoldersRepositoryOLD {
  public Map<String, Folder> getFolders(String workGroupKey, String parentFolderKey) {
    Map<String, Folder> folders = new HashMap<>();

    try (Connection con = DBCPDataSource.getConnection()) {
      String sqlQuery;
      PreparedStatement pstmt;

      if (parentFolderKey == null ) {
        sqlQuery = "SELECT * FROM folders WHERE workgroupkey = ? AND parentfolderkey IS NULL";
        pstmt = con.prepareStatement(sqlQuery);
        pstmt.setString(1, workGroupKey);
      } else {
        sqlQuery = "SELECT * FROM folders WHERE workgroupkey = ? AND parentfolderkey = ?";
        pstmt = con.prepareStatement(sqlQuery);
        pstmt.setString(1, workGroupKey);
        pstmt.setString(2, parentFolderKey);
      }
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
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

        folders.put(key, folder);
      }

      pstmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return folders;
  }
}

package com.example.restservice.templates.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.templates.model.Template;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TemplatesRepository {
  public Map<String, Template> getTemplates(String workGroupKey, String folderKey) {
    Map<String, Template> templates = new HashMap<>();

    try (Connection con = DBCPDataSource.getConnection()) {
      String sqlQuery;
      PreparedStatement pstmt;

      if (folderKey == null ) {
        sqlQuery = "SELECT * FROM templates WHERE workgroupkey = ? AND folderkey IS NULL";
        pstmt = con.prepareStatement(sqlQuery);
        pstmt.setString(1, workGroupKey);
      } else {
        sqlQuery = "SELECT * FROM templates WHERE workgroupkey = ? AND folderkey = ?";
        pstmt = con.prepareStatement(sqlQuery);
        pstmt.setString(1, workGroupKey);
        pstmt.setString(2, folderKey);
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
        boolean isPart = rs.getBoolean("ispart");

        Template template = new Template(
          key,
          name,
          createdUserFirstName,
          createdUserLastName,
          createdUserName,
          createdUserKey,
          createdDateTimeUtc,
          updatedDateTimeUtc,
          folderKey,
          isPart,
          workGroupKey
        );

        templates.put(key, template);
      }

      pstmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return templates;
  }
}

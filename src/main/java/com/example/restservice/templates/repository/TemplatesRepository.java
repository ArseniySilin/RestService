package com.example.restservice.templates.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.folders.model.Folder;
import com.example.restservice.templates.model.Template;
import com.example.restservice.templates.model.TemplateExtended;
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
public class TemplatesRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private List<String> getTemplatesKeysInWorkGroup(Connection con, String workGroupKey) {
    List<String> templatesKeys = new ArrayList<>();

    try {
      PreparedStatement pstmt =
        con.prepareStatement("SELECT * FROM workgroups_templates WHERE workgroupkey = ?");
      pstmt.setString(1, workGroupKey);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        String folderKey = rs.getString("folderkey");
        templatesKeys.add(folderKey);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return templatesKeys;
  }

  public Map<String, Template> getTemplates(String workGroupKey) {
    Map<String, Template> templates = new HashMap<>();

    try {
      Connection con = DBCPDataSource.getConnection();

      List<String> templatesKeys = getTemplatesKeysInWorkGroup(con, workGroupKey);

      if (templatesKeys.isEmpty()) return templates;

      String templatesKeysForSQLQuery = String.join(",", Collections.nCopies(templatesKeys.size(), "?"));
      String sqlQuery = String.format("SELECT * FROM folders WHERE key IN (%s)", templatesKeysForSQLQuery);

      templates = jdbcTemplate.query(
        sqlQuery,
        templatesKeys.toArray(),
        (ResultSet rs) -> {
          HashMap<String, Template> results = new HashMap<>();

          while (rs.next()) {
            String key = rs.getString("key");
            String name = rs.getString("name");
            String createdUserFirstName = rs.getString("createduserfirstname");
            String createdUserLastName = rs.getString("createduserlastname");
            String createdUserName = rs.getString("createdusername");
            String createdUserKey = rs.getString("createduserkey");
            LocalDateTime createdDateTimeUtc = rs.getTimestamp("createddatetimeutc").toLocalDateTime();
            LocalDateTime updatedDateTimeUtc = rs.getTimestamp("updateddatetimeutc").toLocalDateTime();
            String folderKey = rs.getString("folderkey");
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

            results.put(key, template);
          }
          return results;
        });
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return templates;
  }
}

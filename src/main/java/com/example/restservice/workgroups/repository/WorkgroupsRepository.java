package com.example.restservice.workgroups.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.workgroups.model.Workgroup;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class WorkgroupsRepository {
  public Map<String, Workgroup> getWorkgroups(String userKey) {
    Map<String, Workgroup> workgroups = new HashMap<>();

    try {
      Connection con = DBCPDataSource.getConnection();
      PreparedStatement pstmt =
        con.prepareStatement("SELECT * FROM workgroups WHERE createduserkey = ?");
      pstmt.setString(1, userKey);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        String key = rs.getString("key");
        String name = rs.getString("name");
        String publicId = rs.getString("publicid");
        LocalDateTime createdTimeUtc = rs.getTimestamp("createddatetimeutc").toLocalDateTime();
        String createdUserKey = rs.getString("createduserkey");

        Workgroup workgroup = new Workgroup(key, name, publicId, createdTimeUtc, createdUserKey);
        workgroups.put(workgroup.getKey(), workgroup);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return workgroups;
  }
}

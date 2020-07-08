package com.example.restservice.workgroups.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.workgroups.model.Workgroup;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkgroupsRepository {
  public List<Workgroup> getWorkgroups(String userKey) {
    List<Workgroup> workgroups = new ArrayList<>();

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
        workgroups.add(workgroup);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return workgroups;
  }
}

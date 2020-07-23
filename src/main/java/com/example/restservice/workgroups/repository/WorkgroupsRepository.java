package com.example.restservice.workgroups.repository;

import com.example.restservice.DBCPDataSource;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.users.exceptions.UsersException;
import com.example.restservice.users.model.User;
import com.example.restservice.users.repository.UsersRepository;
import com.example.restservice.workgroups.model.Workgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class WorkgroupsRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  UsersRepository usersRepository;

  public Map<String, Workgroup> getWorkGroupsCreatedByUser(String userKey) {
    Map<String, Workgroup> workgroups = new HashMap<>();

    try (
      Connection con = DBCPDataSource.getConnection();
      PreparedStatement pstmt =
        con.prepareStatement("SELECT * FROM workgroups WHERE createduserkey = ?")
      ) {
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

  private List<String> getWorkGroupsKeysIncludingUser(Connection con, String userKey) throws UsersException {
    List<String> workGroupKeys = new ArrayList();

    try (
      PreparedStatement pstmt =
        con.prepareStatement("SELECT * FROM workgroups_users WHERE userkey = ?")
      ) {
      pstmt.setString(1, userKey);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        String workGroupKey = rs.getString("workgroupkey");
        workGroupKeys.add(workGroupKey);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return workGroupKeys;
  }

  public Map<String, Workgroup> getWorkGroupsIncludingUser(String userId) throws EntityNotFoundException {
    Map<String, Workgroup> workGroups = new HashMap<>();

    try (Connection con = DBCPDataSource.getConnection()) {
      String userKey = usersRepository.getUserKeyById(con, userId);

      if (userKey == null) {
        throw new EntityNotFoundException(User.class);
      }

      List<String> workGroupsKeysIncludingUser = getWorkGroupsKeysIncludingUser(con, userKey);

      if (workGroupsKeysIncludingUser.isEmpty()) {
        return workGroups;
      }

      String workGroupKeys = String.join(",", Collections.nCopies(workGroupsKeysIncludingUser.size(), "?"));
      String sqlQuery = String.format("SELECT * FROM workgroups WHERE key IN (%s)", workGroupKeys);

      workGroups = jdbcTemplate.query(
        sqlQuery,
        workGroupsKeysIncludingUser.toArray(),
        (ResultSet rs) -> {
          HashMap<String, Workgroup> results = new HashMap<>();

          while (rs.next()) {
            String key = rs.getString("key");
            String name = rs.getString("name");
            String publicId = rs.getString("publicid");
            LocalDateTime createdTimeUtc = rs.getTimestamp("createddatetimeutc").toLocalDateTime();
            Workgroup workGroup = new Workgroup(key, name, publicId, createdTimeUtc, userKey);

            results.put(key, workGroup);
          }
          return results;
      });
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: add custom exception
    }

    return workGroups;
  }
}

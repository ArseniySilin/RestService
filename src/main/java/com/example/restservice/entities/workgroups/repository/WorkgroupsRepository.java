package com.example.restservice.entities.workgroups.repository;

import com.example.restservice.entities.workgroups.model.Workgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkgroupsRepository extends JpaRepository<Workgroup, String> {
  @Query("SELECT w FROM Workgroup w WHERE w.createdUserKey = :createdUserKey")
  List<Workgroup> findByCreatedUserKey(@Param("createdUserKey") String createdUserKey);

  @Query(
    value = "SELECT * FROM workgroups WHERE key IN (SELECT workgroupkey FROM workgroups_users WHERE userkey = ?1)",
    nativeQuery = true
  )
  List<Workgroup> findByUserKey(@Param("userKey") String userKey);
}

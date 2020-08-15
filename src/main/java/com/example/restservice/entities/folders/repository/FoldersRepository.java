package com.example.restservice.entities.folders.repository;

import com.example.restservice.entities.folders.model.Folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FoldersRepository extends JpaRepository<Folder, String> {
  @Query("SELECT f FROM Folder f WHERE f.workGroupKey = :workGroupKey AND (:parentFolderKey is NULL OR f.parentFolderKey = :parentFolderKey)")
  List<Folder> findByWorkGroupKeyAndParentFolderKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("parentFolderKey") String parentFolderKey
  );

  @Query("SELECT f FROM Folder f WHERE f.workGroupKey = :workGroupKey")
  List<Folder> findByWorkGroupKey(
    @Param("workGroupKey") String workGroupKey
  );

  @Modifying
  @Query("UPDATE Folder f SET f.name = :name WHERE f.key = :key")
  void setFolderNameByKey(
    @Param("name") String name,
    @Param("key") String key);

  @Modifying
  @Query("UPDATE Folder f SET f.name = :name WHERE f.workGroupKey = :workGroupKey AND f.key = :key")
  void setFolderNameByWorkGroupKeyAndKey(
    @Param("name") String name,
    @Param("workGroupKey") String workGroupKey,
    @Param("key") String key);

  @Query("SELECT f FROM Folder f WHERE f.workGroupKey = :workGroupKey AND f.key = :key")
  Folder findByWorkGroupKeyAndKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("key") String key
  );
}

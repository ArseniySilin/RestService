package com.example.restservice.folders.repository;

import com.example.restservice.folders.model.Folder;

import org.springframework.data.jpa.repository.JpaRepository;
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
}

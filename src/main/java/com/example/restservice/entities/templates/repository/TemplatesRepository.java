package com.example.restservice.entities.templates.repository;

import com.example.restservice.entities.templates.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplatesRepository extends JpaRepository<Template, String> {
  @Query("SELECT t FROM Template t WHERE t.workGroupKey = :workGroupKey AND (:folderKey is NULL OR t.folderKey = :folderKey)")
  List<Template> findByWorkGroupKeyAndFolderKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("folderKey") String folderKey
  );

  @Modifying
  @Query("DELETE FROM Template t WHERE t.workGroupKey = :workGroupKey AND t.key = :key")
  void deleteByWorkGroupKeyAndKey(
    @Param("workGroupKey") String workGroupKey,
    @Param("key") String key
  );
}

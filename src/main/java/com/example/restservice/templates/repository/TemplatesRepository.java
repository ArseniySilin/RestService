package com.example.restservice.templates.repository;

import com.example.restservice.templates.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
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
}

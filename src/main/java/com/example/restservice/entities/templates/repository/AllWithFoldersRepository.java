package com.example.restservice.entities.templates.repository;

import com.example.restservice.entities.templates.model.TemplatesAllWithFoldersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllWithFoldersRepository extends JpaRepository<TemplatesAllWithFoldersEntity, String> {
  @Query(nativeQuery = true)
  Page<TemplatesAllWithFoldersEntity> findAllWithFolders(
      @Param("workGroupKey") String workGroupKey,
      @Param("createdUserKey") String createdUserKey,
      @Param("folderKey") String folderKey,
      Pageable pageable
    );
}

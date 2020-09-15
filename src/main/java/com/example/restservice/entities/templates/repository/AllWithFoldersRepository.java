package com.example.restservice.entities.templates.repository;

import com.example.restservice.entities.templates.model.TemplatesAllWithFoldersPage22;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllWithFoldersRepository extends JpaRepository<TemplatesAllWithFoldersPage22, String> {
  @Query(nativeQuery = true)
  List<TemplatesAllWithFoldersPage22> findAllWithFolders();
}

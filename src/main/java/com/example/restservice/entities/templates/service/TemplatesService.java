package com.example.restservice.entities.templates.service;

import com.example.restservice.entities.templates.model.*;
import com.example.restservice.entities.templates.repository.AllWithFoldersRepository;
import com.example.restservice.entities.users.service.UsersService;
import com.example.restservice.utils.jwt.JwtTokenUtil;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.entities.folders.model.Folder;
import com.example.restservice.entities.folders.repository.FoldersRepository;
import com.example.restservice.entities.templates.repository.TemplatesRepository;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.entities.workgroups.repository.WorkgroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TemplatesService {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private WorkgroupsRepository workgroupsRepository;

  @Autowired
  private TemplatesRepository templatesRepository;

  @Autowired
  FoldersRepository foldersRepository;

  @Autowired
  UsersService usersService;

  @Autowired
  AllWithFoldersRepository allWithFoldersRepository;

  public TemplatesAllWithFoldersPage getAllWithFoldersPage(
    String workGroupKey,
    String userKey,
    String folderKey,
    Pageable pageable) {

    Page<TemplatesAllWithFoldersEntity> page =
      allWithFoldersRepository.findAllWithFolders(workGroupKey, userKey, folderKey, pageable);

    PageInfo pageInfo = new PageInfo(
      page.hasNext(),
      page.hasPrevious(),
      0,
      page.getNumberOfElements(),
      pageable.getPageNumber() + 1,
      pageable.getPageNumber() + 1,
      (int) page.getTotalElements(),
      page.getTotalPages()
    );

    List<Template> templates = new ArrayList<>();
    List<Folder> folders = new ArrayList<>();
    List<TemplatesAllWithFoldersEntity> result = page.getContent();

    for (TemplatesAllWithFoldersEntity entity : result) {
      if (entity.getEntityType().equals("template")) {
        Template template = new Template(
          entity.getKey(),
          entity.getWorkGroupKey(),
          entity.getName(),
          entity.getCreatedUserFirstName(),
          entity.getCreatedUserLastName(),
          entity.getCreatedUserName(),
          entity.getCreatedUserKey(),
          entity.getCreatedDateTimeUtc(),
          entity.getUpdatedDateTimeUtc(),
          entity.getFolderKey(),
          entity.isPart()
        );
        templates.add(template);
      }
      if (entity.getEntityType().equals("folder")) {
        Folder folder = new Folder(
          entity.getKey(),
          entity.getWorkGroupKey(),
          entity.getName(),
          entity.getCreatedUserFirstName(),
          entity.getCreatedUserLastName(),
          entity.getCreatedUserName(),
          entity.getCreatedUserKey(),
          entity.getCreatedDateTimeUtc(),
          entity.getUpdatedDateTimeUtc(),
          entity.getFolderType(),
          entity.getParentFolderKey(),
          entity.getParentFolderName()
        );
        folders.add(folder);
      }
    }

    TemplatesAllWithFoldersPage templatesAllWithFoldersPage =
      new TemplatesAllWithFoldersPage(templates, folders, pageInfo);

    return templatesAllWithFoldersPage;
  }

  public void saveTemplate(String token, String workGroupKey, CreateTemplateRequest templateRequest) {
    String userName = jwtTokenUtil.getUsernameFromToken(token);
    User user = usersService.getUser(userName);

    String key = UUID.randomUUID().toString();
    String name = templateRequest.getName();
    String createdUserFirstName = user.getFirstName();
    String createdUserLastName = user.getLastName();
    String createdUserName = user.getUserName();
    String createdUserKey = user.getKey();
    LocalDateTime createdDateTimeUtc = LocalDateTime.now();
    LocalDateTime updatedDateTimeUtc = LocalDateTime.now();
    String folderKey = templateRequest.getFolderKey();
    boolean isPart = templateRequest.isPart();

    Template template = new Template(
      key,
      workGroupKey,
      name,
      createdUserFirstName,
      createdUserLastName,
      createdUserName,
      createdUserKey,
      createdDateTimeUtc,
      updatedDateTimeUtc,
      folderKey,
      isPart
    );

    templatesRepository.save(template);
  }

  public void deleteTemplate(String workGroupKey, String key) {
    Template template = templatesRepository.findByWorkGroupKeyAndKey(workGroupKey, key);

    if (template != null) {
      templatesRepository.deleteByWorkGroupKeyAndKey(workGroupKey, key);
    } else {
      throw new EntityNotFoundException(Template.class);
    }
  }

  public void moveTemplateToFolder(String workGroupKey, String key, String nextFolderKey)
    throws EntityNotFoundException {
    Template template = templatesRepository.findByWorkGroupKeyAndKey(workGroupKey, key);

    if (template != null) {
      templatesRepository.moveToFolder(workGroupKey, key, nextFolderKey);
    } else {
      throw new EntityNotFoundException(Template.class);
    }
  }
}

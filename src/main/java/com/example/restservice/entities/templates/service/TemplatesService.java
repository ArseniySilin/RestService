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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

  public List<TemplatesAllWithFoldersPage22> getAllWithFoldersPage2() {
    return allWithFoldersRepository.findAllWithFolders("aaaaa111-1a1a-aa11-11a1-11111a111111", "test@docspro.ru");
  }

  public TemplatesAllWithFoldersPage getAllWithFoldersPage(String workGroupKey, Map<String, String> queryParams)
    throws EntityNotFoundException {
    String folderKey = queryParams.get("folderKey");
    String pageNumber = queryParams.getOrDefault("pageNumber", "1");
    String itemsPerPage = queryParams.getOrDefault("itemsPerPage", "15");
    String columnToOrderBy = queryParams.getOrDefault("columnToOrderBy", "2");
    String orderBy = queryParams.getOrDefault("orderBy", "0");

    // TODO: get folders and templates in parallel

    // get all folders in workGroup
    List<Folder> folders = foldersRepository.findByWorkGroupKeyAndParentFolderKey(workGroupKey, folderKey);

    // get all templates in workGroup
    List<Template> templates = templatesRepository.findByWorkGroupKeyAndFolderKey(workGroupKey, folderKey);

    // build page
    TemplatesAllWithFoldersPageBuilder pb = new TemplatesAllWithFoldersPageBuilder(
      templates,
      folders,
      itemsPerPage,
      columnToOrderBy,
      orderBy
    );
    // TODO: add validation and exception handling
    TemplatesAllWithFoldersPage page = pb.getPage(Integer.parseInt(pageNumber));

    return page;
  }

  public void saveTemplate(String token, String workGroupKey, CreateTemplateRequest templateRequest) {
//    User user = usersService.getAuthorizedUser(token, workGroupKey);
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

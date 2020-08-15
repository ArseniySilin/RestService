package com.example.restservice.entities.templates.service;

import com.example.restservice.utils.jwt.JwtTokenUtil;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.entities.folders.model.Folder;
import com.example.restservice.entities.folders.repository.FoldersRepository;
import com.example.restservice.entities.templates.model.CreateTemplateRequest;
import com.example.restservice.entities.templates.model.Template;
import com.example.restservice.entities.templates.model.TemplatesAllWithFoldersPage;
import com.example.restservice.entities.templates.model.TemplatesAllWithFoldersPageBuilder;
import com.example.restservice.entities.templates.repository.TemplatesRepository;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.entities.users.service.UsersService;
import com.example.restservice.entities.workgroups.model.Workgroup;
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

  public TemplatesAllWithFoldersPage getAllWithFoldersPage(String token, String workGroupKey, Map<String, String> queryParams)
    throws EntityNotFoundException {
    String userId = jwtTokenUtil.getUserIdFromBearerToken(token);

    if (userId == null) {
      // TODO: replace with invalid token exception
      throw new EntityNotFoundException(com.example.restservice.entities.users.model.User.class);
    }

    String folderKey = queryParams.get("folderKey");
    String pageNumber = queryParams.getOrDefault("pageNumber", "1");
    String itemsPerPage = queryParams.getOrDefault("itemsPerPage", "15");
    String columnToOrderBy = queryParams.getOrDefault("columnToOrderBy", "2");
    String orderBy = queryParams.getOrDefault("orderBy", "0");

    // TODO: move this validation to some filter
    // check if user exists in workGroup
    Map<String, Workgroup> workGroups = workgroupsRepository.getWorkGroupsIncludingUser(userId);

    if (workGroups.isEmpty()) {
      throw new EntityNotFoundException(Workgroup.class);
    }

    // TODO: get folders and templates in parallel using Thread Pool

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
    User user = usersService.getAuthorizedUser(token, workGroupKey);

    String key = UUID.randomUUID().toString();
    String name = templateRequest.getName();
    String createdUserFirstName = user.getFirstName();
    String createdUserLastName = user.getLastName();
    String createdUserName = user.getUsername();
    String createdUserKey = user.getKey();
    LocalDateTime createdDateTimeUtc = LocalDateTime.now();
    LocalDateTime updatedDateTimeUtc = LocalDateTime.now();
    String folderKey = templateRequest.getFolderKey();
    boolean isPart = templateRequest.isPart();

    Template template = new Template(
      key,
      name,
      createdUserFirstName,
      createdUserLastName,
      createdUserName,
      createdUserKey,
      createdDateTimeUtc,
      updatedDateTimeUtc,
      folderKey,
      isPart,
      workGroupKey
    );

    templatesRepository.save(template);
  }

  public void deleteTemplate(String workGroupKey, String key) {
    templatesRepository.deleteByWorkGroupKeyAndKey(workGroupKey, key);
  }
}

package com.example.restservice.templates.service;

import com.example.restservice.JwtTokenUtil;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.templates.model.TemplatesAllWithFolders;
import com.example.restservice.templates.repository.TemplatesRepository;
import com.example.restservice.workgroups.model.Workgroup;
import com.example.restservice.workgroups.repository.WorkgroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TemplatesService {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private WorkgroupsRepository workgroupsRepository;

  @Autowired
  private TemplatesRepository templatesRepository;

  public TemplatesAllWithFolders getAllWithFolders(String token, String workGroupKey, Map<String, String> queryParams)
    throws EntityNotFoundException {
    String userKey = jwtTokenUtil.getUserIdFromBearerToken(token);

    if (userKey == null) {
      // TODO: replace with invalid token exception
      throw new EntityNotFoundException(com.example.restservice.users.model.User.class);
    }

    String folderKey = queryParams.get("folderKey");
    String pageNumber = queryParams.getOrDefault("pageNumber", "1");
    String itemsPerPage = queryParams.getOrDefault("itemsPerPage", "15");
    String columnToOrderBy = queryParams.getOrDefault("columnToOrderBy", "2");
    String orderBy = queryParams.getOrDefault("orderBy", "0");

    // check if user exists in workGroup
//    Map<String, Workgroup> workGroups = workgroupsRepository.getWorkgroups(userKey);

//    if (workGroups.isEmpty()) {
//      throw new EntityNotFoundException(Workgroup.class);
//    }

    // get all folders in workGroup
//    if (folderKey != null) {
//      List<String> = workgroupsRepository.getWorkgroupFolders(folderKey);
//    }


    // get all templates in workgroup

    // build TemplatesAllWithFolders

    return null;
  }

}

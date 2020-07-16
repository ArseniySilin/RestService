package com.example.restservice.templates.service;

import com.example.restservice.JwtTokenUtil;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.templates.model.TemplatesAllWithFolders;
import com.example.restservice.templates.repository.TemplatesRepository;
import com.example.restservice.workgroups.repository.WorkgroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplatesService {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private WorkgroupsRepository workgroupsRepository;

  @Autowired
  private TemplatesRepository templatesRepository;

//  public TemplatesAllWithFolders getAllWithFolders(String token, String workGroupKey) throws EntityNotFoundException {
//    String userKey = jwtTokenUtil.getUserIdFromBearerToken(token);
//
//    if (userKey == null) {
//      throw new EntityNotFoundException(com.example.restservice.User.class);
//    }
//
//    // check if user in this workgroup
//    // get all folders in workgroup
//    // get all templates in workgroup
//    // build TemplatesAllWithFolders
//
//  }

}

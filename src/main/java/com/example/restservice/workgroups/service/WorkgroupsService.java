package com.example.restservice.workgroups.service;

import com.example.restservice.JwtTokenUtil;
import com.example.restservice.users.model.User;
import com.example.restservice.workgroups.repository.WorkgroupsRepository;
import com.example.restservice.workgroups.model.Workgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservice.execptions.EntityNotFoundException;
import java.util.Map;

@Service
public class WorkgroupsService {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private WorkgroupsRepository workgroupsRepository;

  public Map<String, Workgroup> getWorkgroups(String token) throws EntityNotFoundException {
    String userKey = jwtTokenUtil.getUserIdFromBearerToken(token);

    if (userKey == null) {
      throw new EntityNotFoundException(User.class);
    }

    Map<String, Workgroup> workgroups = workgroupsRepository.getWorkgroups(userKey);

    if (workgroups.isEmpty()) {
      throw new EntityNotFoundException(Workgroup.class, "userKey", userKey);
    }

    return workgroups;
  }
}
package com.example.restservice.entities.workgroups.service;

import com.example.restservice.utils.jwt.JwtTokenUtil;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.entities.workgroups.repository.WorkgroupsRepository;
import com.example.restservice.entities.workgroups.model.Workgroup;
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

  public Map<String, Workgroup> getWorkgroupsCreatedByUser(String userKey) throws EntityNotFoundException {
    if (userKey == null) {
      // TODO: throw InvalidTokenException
    }

    Map<String, Workgroup> workgroups = workgroupsRepository.getWorkGroupsCreatedByUser(userKey);

    if (workgroups.isEmpty()) {
      throw new EntityNotFoundException(Workgroup.class, "userKey", userKey);
    }

    return workgroups;
  }

  public Map<String, Workgroup> getWorkGroupsIncludingUser(String userId) throws EntityNotFoundException {
    if (userId == null) {
      // TODO: throw InvalidTokenException
    }

    Map<String, Workgroup> workgroups = workgroupsRepository.getWorkGroupsIncludingUser(userId);

    return workgroups;
  }

  public boolean isWorkGroupIncludesUser(String workGroupKey, User user) {
    // TODO: implement logic
    return true;
  }
}

package com.example.restservice.entities.workgroups.service;

import com.example.restservice.entities.workgroups.model.Workgroup;
import com.example.restservice.entities.workgroups.repository.WorkgroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkgroupsService {
  @Autowired
  WorkgroupsRepository workgroupsRepository;

  public List<Workgroup> getWorkGroupsIncludingUser(String userKey) {
    return workgroupsRepository.findByUserKey(userKey);
  }
}

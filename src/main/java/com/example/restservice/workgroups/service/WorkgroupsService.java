package com.example.restservice.workgroups.service;

import com.example.restservice.workgroups.repository.WorkgroupsRepository;
import com.example.restservice.workgroups.model.Workgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservice.execptions.EntityNotFoundException;
import java.util.List;

@Service
public class WorkgroupsService {

  @Autowired
  private WorkgroupsRepository workgroupsRepository;

  public List<Workgroup> getWorkgroups(String userId) {
    List<Workgroup> workgroups = workgroupsRepository.getWorkgroups(userId);

    if (workgroups.isEmpty()) {
      throw new EntityNotFoundException(Workgroup.class, "userId", userId);
    }

    return workgroups;
  }
}

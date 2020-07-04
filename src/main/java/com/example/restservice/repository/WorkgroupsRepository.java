package com.example.restservice.repository;

import org.springframework.stereotype.Repository;
import com.example.restservice.model.Workgroup;

import java.util.ArrayList;

@Repository
public class WorkgroupsRepository {
  public Workgroup getWorkgroup(String key) {
    return new Workgroup(key, "name", "publicId", "userKey");
  }

  public ArrayList<Workgroup> getWorkgroups(String userKey) {
    ArrayList<Workgroup> workgroups = new ArrayList();
    workgroups.add(new Workgroup(userKey, "name", "publicId", "userKey"));

    return workgroups;
  }
}

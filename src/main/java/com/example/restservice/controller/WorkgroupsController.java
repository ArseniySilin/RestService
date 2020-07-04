package com.example.restservice.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.repository.WorkgroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.example.restservice.model.Workgroup;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class WorkgroupsController {
  @Autowired
  private WorkgroupsRepository workgroupsRepository;

  @GetMapping("/workgroups")
  public ResponseEntity<CommonResponse> getWorkgroups(@RequestHeader("authorization") String token) {
    System.out.println("token: " + token);

    ArrayList<Workgroup> workgroups = new ArrayList<>(workgroupsRepository.getWorkgroups("2"));

    return ResponseEntity.ok(new CommonResponse("SUCCESS", "0", null, workgroups));
  }
}

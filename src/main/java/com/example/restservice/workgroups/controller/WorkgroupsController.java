package com.example.restservice.workgroups.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.JwtTokenUtil;
import com.example.restservice.Messages;
import com.example.restservice.workgroups.repository.WorkgroupsRepository;
import com.example.restservice.workgroups.service.WorkgroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.example.restservice.workgroups.model.Workgroup;
import com.example.restservice.execptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class WorkgroupsController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private WorkgroupsService workgroupsService;

  @GetMapping("/workgroups")
  public ResponseEntity<CommonResponse> getWorkgroups(@RequestHeader("authorization") String token)
    throws EntityNotFoundException {

    String userKey = jwtTokenUtil.getUserIdFromBearerToken(token);

    if (userKey == null) {
      throw new EntityNotFoundException(com.example.restservice.User.class);
    }

    List<Workgroup> workgroups = workgroupsService.getWorkgroups(userKey);

    return ResponseEntity.ok(new CommonResponse(
      Messages.SUCCESS.message,
      Messages.SUCCESS.code,
      null,
      workgroups
    ));
  }
}

package com.example.restservice.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.JwtTokenUtil;
import com.example.restservice.Messages;
import com.example.restservice.workgroups.WorkgroupsRepository;
import com.example.restservice.workgroups.WorkgroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.example.restservice.model.Workgroup;
import com.example.restservice.execptions.EntityNotFoundException;

import java.util.ArrayList;

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
    System.out.println("token: " + token);

    String tokenHeader = jwtTokenUtil.tokenHeader;
    String userId = null;

    if (token.startsWith(tokenHeader)) {
      String tokenWithoutHeader = token.substring(tokenHeader.length());
      userId = jwtTokenUtil.getUserIdFromToken(tokenWithoutHeader);
    } else {
      // throw exception
    }

    System.out.println("userId: " + userId);

    ArrayList<Workgroup> workgroups = new ArrayList<>(workgroupsService.getWorkgroups(userId));

    return ResponseEntity.ok(new CommonResponse(
      Messages.SUCCESS.message,
      Messages.SUCCESS.code,
      null,
      workgroups
    ));
  }
}

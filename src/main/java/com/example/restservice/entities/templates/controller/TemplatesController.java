package com.example.restservice.entities.templates.controller;

import com.example.restservice.entities.common.CommonResponse;
import com.example.restservice.Messages;
import com.example.restservice.entities.templates.model.*;
import com.example.restservice.entities.templates.service.TemplatesService;
import com.example.restservice.entities.users.service.UsersService;
import com.example.restservice.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TemplatesController {
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Autowired
  private TemplatesService templatesService;

  @Autowired
  private UsersService usersService;

  @GetMapping(value = "WorkGroups/{workGroupKey}/Templates/AllWithFolders")
  public ResponseEntity<CommonResponse> getAllWithFoldersPage(
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestParam Map<String, String> queryParams) {

    TemplatesAllWithFoldersPage templatesAllWithFolders = templatesService.getAllWithFoldersPage(workGroupKey, queryParams);

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, templatesAllWithFolders)
    );
  }

  @GetMapping(value = "WorkGroups/{workGroupKey}/Templates/AllWithFolders2")
  public ResponseEntity<CommonResponse> getAllWithFoldersPage2(
    @RequestHeader("authorization") String token,
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestParam Map<String, String> queryParams) {

    String userKey = jwtTokenUtil.getUserKeyFromToken(token);
    Pageable firstPageWithTwoElements = PageRequest.of(0, 2);

    System.out.println("userKey: " + userKey);
    System.out.println("workGroupKey: " + workGroupKey);

    return ResponseEntity.ok(
      new CommonResponse(
        Messages.SUCCESS.message,
        Messages.SUCCESS.code,
        null,
        templatesService.getAllWithFoldersPage2(workGroupKey, userKey, firstPageWithTwoElements))
    );
  }

  @PostMapping(value = "WorkGroups/{workGroupKey}/Templates")
  public ResponseEntity<CommonResponse> createTemplate(
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestBody CreateTemplateRequest createTemplateRequest,
    @RequestHeader("authorization") String token
  ) {
    templatesService.saveTemplate(token, workGroupKey, createTemplateRequest);

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
    );
  }

  @PostMapping(value = "WorkGroups/{workGroupKey}/Templates/{key}/moveToFolder")
  public ResponseEntity<CommonResponse> moveTemplateToFolder(
    @PathVariable("workGroupKey") String workGroupKey,
    @PathVariable("key") String key,
    @RequestBody MoveTemplateToFolderRequest moveTemplateToFolderRequest
  ) {
    templatesService.moveTemplateToFolder(workGroupKey, key, moveTemplateToFolderRequest.getFolderKey());

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
    );
  }

  @DeleteMapping(value = "WorkGroups/{workGroupKey}/Templates/{key}")
  public ResponseEntity<CommonResponse> deleteTemplate(
    @PathVariable("workGroupKey") String workGroupKey,
    @PathVariable("key") String key
  ) {
    templatesService.deleteTemplate(workGroupKey, key);

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
    );
  }
}

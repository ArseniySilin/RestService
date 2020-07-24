package com.example.restservice.templates.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.Messages;
import com.example.restservice.templates.model.CreateTemplateRequest;
import com.example.restservice.templates.model.TemplatesAllWithFoldersPage;
import com.example.restservice.templates.service.TemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class TemplatesController {

  @Autowired
  private TemplatesService templatesService;

  @GetMapping(value = "WorkGroups/{workGroupKey}/Templates/AllWithFolders")
  public ResponseEntity<CommonResponse> getAllWithFoldersPage(
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestParam Map<String, String> queryParams,
    @RequestHeader("authorization") String token) {

    TemplatesAllWithFoldersPage templatesAllWithFolders = templatesService.getAllWithFoldersPage(token, workGroupKey, queryParams);

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, templatesAllWithFolders)
    );
  }

  @PostMapping(value = "WorkGroups/{workGroupKey}/Templates")
  public ResponseEntity<CommonResponse> createTemplate(
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestBody CreateTemplateRequest createTemplateRequest
  ) {
    return ResponseEntity.ok(null);
  }
}

package com.example.restservice.templates.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.Messages;
import com.example.restservice.templates.service.TemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/templates")
public class TemplatesController {

  @Autowired
  private TemplatesService templatesService;

  @GetMapping(value = "/{workGroupKey}/AllWithFolders")
  public ResponseEntity<CommonResponse> getAllWithFolders(
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestParam Map<String, String> queryParams,
    @RequestHeader("authorization") String token) {

    templatesService.getAllWithFolders(token, workGroupKey, queryParams);

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, ""));
  }
}

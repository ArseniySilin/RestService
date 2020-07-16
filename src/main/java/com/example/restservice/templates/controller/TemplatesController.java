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
    String folderKey = queryParams.get("folderKey");
    String pageNumber = queryParams.getOrDefault("pageNumber", "1");
    String itemsPerPage = queryParams.getOrDefault("itemsPerPage", "15");
    String columnToOrderBy = queryParams.getOrDefault("columnToOrderBy", "2");
    String orderBy = queryParams.getOrDefault("orderBy", "0");

//    templatesService.getAllWithFolders(token, workGroupKey);

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, ""));
  }


}

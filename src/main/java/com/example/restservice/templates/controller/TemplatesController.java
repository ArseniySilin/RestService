package com.example.restservice.templates.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.Greeting;
import com.example.restservice.Messages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/templates")
public class TemplatesController {

  @GetMapping(value = "/AllWithFolders")
  public ResponseEntity<CommonResponse> getAllWithFolders(@RequestParam Map<String, String> queryParams) {
    String folderKey = queryParams.get("folderKey");
    String pageNumber = queryParams.getOrDefault("pageNumber", "1");
    String itemsPerPage = queryParams.getOrDefault("itemsPerPage", "15");
    String columnToOrderBy = queryParams.getOrDefault("columnToOrderBy", "2");
    String orderBy = queryParams.getOrDefault("orderBy", "0");

    return ResponseEntity.ok(new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, ""));
  }

}

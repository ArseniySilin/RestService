package com.example.restservice.templates.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.Messages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TemplatesController {

  @GetMapping(value = "/AllWithFolders")
  public ResponseEntity<CommonResponse> getAllWithValues(
    @RequestParam String folderKey,
    @RequestParam(defaultValue = "1") String pageNumber,
    @RequestParam(defaultValue = "15") String itemsPerPage,
    @RequestParam(defaultValue = "2") String columnToOrderBy,
    @RequestParam(defaultValue = "0") String orderBy
  ) {

    return ResponseEntity.ok(new CommonResponse(
      Messages.SUCCESS.message,
      Messages.SUCCESS.code,
      null,
      "data"
    ));
  }

}

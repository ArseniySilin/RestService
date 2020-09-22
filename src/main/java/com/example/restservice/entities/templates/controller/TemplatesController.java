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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@CrossOrigin
@Validated
public class TemplatesController {
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Autowired
  private TemplatesService templatesService;

  @Autowired
  private UsersService usersService;

  @GetMapping(value = "WorkGroups/{workGroupKey}/Templates/AllWithFolders")
  public ResponseEntity<CommonResponse> getAllWithFoldersPage(
    @RequestHeader("authorization") String token,
    @PathVariable("workGroupKey") String workGroupKey,
    @RequestParam(value = "folderKey", required = false) String folderKey,
    @Min(1) @RequestParam(value = "pageNumber") String pageNumber,
    @Min(1) @RequestParam(value = "itemsPerPage", required = false, defaultValue = "15") String itemsPerPage,
    @Min(1) @Max(5) @RequestParam(value = "columnToOrderBy", required = false, defaultValue = "2") String columnToOrderBy,
    @Min(0) @Max(1) @RequestParam(value = "orderBy", required = false, defaultValue = "0") String orderBy
  ) {
    String userKey = jwtTokenUtil.getUserKeyFromToken(token);
    String columnNameToOrderBy = Columns.indicies.getOrDefault(columnToOrderBy, Columns.indicies.get("2"));

    Pageable pr = PageRequest.of(
      Integer.parseInt(pageNumber) - 1,
      Integer.parseInt(itemsPerPage),
      Sort.by(orderBy.equals("0") ? Sort.Direction.DESC : Sort.Direction.ASC, columnNameToOrderBy)
    );

    return ResponseEntity.ok(
      new CommonResponse(
        Messages.SUCCESS.message,
        Messages.SUCCESS.code,
        null,
        templatesService.getAllWithFoldersPage(
          workGroupKey,
          userKey,
          folderKey,
          pr
        )
      )
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

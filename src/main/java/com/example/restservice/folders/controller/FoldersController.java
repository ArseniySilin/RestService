package com.example.restservice.folders.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.Messages;
import com.example.restservice.folders.model.CreateFolderRequest;
import com.example.restservice.folders.model.Folder;
import com.example.restservice.folders.service.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/WorkGroups/{workGroupKey}")
public class FoldersController {
  @Autowired
  FoldersService foldersService;

  @GetMapping(value = "/TemplateFolder/All")
  public ResponseEntity<CommonResponse> getFolders(@PathVariable String workGroupKey) {
    List<Folder> folderList =
      foldersService.getFolders(workGroupKey);
    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, folderList)
    );
  }

  @PostMapping(value = "/TemplateFolder")
  public ResponseEntity<CommonResponse> createFolder(
    @RequestBody CreateFolderRequest createFolderRequest,
    @RequestHeader("authorization") String token) {
      foldersService.saveFolder(token, createFolderRequest);

      return ResponseEntity.ok(
        new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
      );
  }

  @DeleteMapping(value = "/TemplateFolder/{key}")
  public ResponseEntity<CommonResponse> deleteFolder(
    @RequestHeader("authorization") String token,
    @PathVariable String workGroupKey,
    @PathVariable String key) {

      foldersService.deleteFolder(token, key, workGroupKey);

      return ResponseEntity.ok(
        new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
      );
  }
}

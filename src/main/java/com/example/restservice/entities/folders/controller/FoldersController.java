package com.example.restservice.entities.folders.controller;

import com.example.restservice.entities.common.CommonResponse;
import com.example.restservice.Messages;
import com.example.restservice.entities.folders.model.CommonFolderRequest;
import com.example.restservice.entities.folders.model.Folder;
import com.example.restservice.entities.folders.service.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/WorkGroups/{workGroupKey}")
public class FoldersController {
  @Autowired
  FoldersService foldersService;

  @GetMapping(value = "/TemplateFolder/All")
  public ResponseEntity<CommonResponse> getFoldersAll(
    @PathVariable String workGroupKey,
    @RequestParam String folderKey
  ) {
    List<Folder> folderList =
      foldersService.getFolders(workGroupKey, folderKey);
    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, folderList)
    );
  }

  @GetMapping(value = "/TemplateFolder/{key}")
  public ResponseEntity<CommonResponse> getFolder(
    @PathVariable String workGroupKey,
    @PathVariable String key
  ) {
    Folder folder =
      foldersService.getFolder(workGroupKey, key);
    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, folder)
    );
  }

  @PostMapping(value = "/TemplateFolder")
  public ResponseEntity<CommonResponse> createFolder(
    @RequestBody CommonFolderRequest commonFolderRequest,
    @RequestHeader("authorization") String token) {
      foldersService.saveFolder(token, commonFolderRequest);

      return ResponseEntity.ok(
        new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
      );
  }

  @PutMapping(value = "/TemplateFolder/{key}")
  public ResponseEntity<CommonResponse> updateFolder(
    @PathVariable String key,
    @PathVariable String workGroupKey,
    @RequestBody CommonFolderRequest commonFolderRequest
  ) {
    Folder updatedFolder = foldersService.updateFolder(workGroupKey, key, commonFolderRequest);

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, updatedFolder)
    );
  }

  @DeleteMapping(value = "/TemplateFolder/{key}")
  public ResponseEntity<CommonResponse> deleteFolder(
    @PathVariable String workGroupKey,
    @PathVariable String key
  ) {
    foldersService.deleteFolder(key, workGroupKey);

    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, null)
    );
  }
}
